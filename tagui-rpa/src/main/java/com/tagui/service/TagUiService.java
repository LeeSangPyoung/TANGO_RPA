package com.tagui.service;

import com.tagui.entity.RpaAction;
import com.tagui.entity.RpaSubAction;
import com.tagui.entity.RpaAccount;
import com.tagui.mapper.RpaActionMapper;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class TagUiService {
    private final RpaActionMapper actionMapper;
    private static final String TAGUI_EXECUTABLE = "D:\\TagUI_Windows\\tagui\\src\\tagui.cmd";
    private static final String BASE_SCRIPT_PATH = "D:\\TagUI_Windows\\tagui\\scripts\\";
    private static final Logger logger = Logger.getLogger(TagUiService.class.getName());

    public TagUiService(RpaActionMapper actionMapper) {
        this.actionMapper = actionMapper;
    }

    public String executeRpaByActionId(String actionId) {
        try {
            logger.info("🔎 액션 ID로 실행할 액션을 조회: " + actionId);
            RpaAction rpaAction = actionMapper.findActionByActionId(actionId);
            if (rpaAction == null) {
                return "❌ 해당 액션 ID에 대한 정보 없음";
            }

            String systemId = rpaAction.getSystemId();
            String domainUrl = actionMapper.findDomainBySystemId(systemId);
            if (domainUrl == null) {
                return "❌ 시스템 ID에 대한 도메인 정보 없음";
            }

            // 🆕 실행 순서별 개수 체크
            List<Map<String, Object>> executionCounts = actionMapper.countSubActionsByExecuteOrder(actionId);
            int previousCount = -1;
            for (Map<String, Object> countEntry : executionCounts) {
                int currentCount = ((Number) countEntry.get("count")).intValue();
                if (previousCount != -1 && previousCount != currentCount) {
                    throw new RuntimeException("⚠️ 실행 순서별 개수가 일치하지 않음 (execute_order 개수 불일치)");
                }
                previousCount = currentCount;
            }

            // 🆕 실행 순서별로 그룹화
            List<RpaSubAction> subActions = actionMapper.findSubActionsByActionId(actionId);
            Map<Integer, List<RpaSubAction>> groupedActions = subActions.stream()
                .collect(Collectors.groupingBy(RpaSubAction::getExecuteOrder));

            // 🆕 각 `sub_action_id`별 계정 정보 조회 (계정 여러 개일 경우 리스트로 매핑)
            Map<String, List<RpaAccount>> accountMap = actionMapper.findAccountsByActionId(actionId).stream()
                .collect(Collectors.groupingBy(account -> account.getSubActionId().toString()));

            // 🆕 실행 순서별로 TagUI 실행
            for (Map.Entry<Integer, List<RpaSubAction>> entry : groupedActions.entrySet()) {
                int executeOrder = entry.getKey();
                List<RpaSubAction> actions = entry.getValue();

                // 🆕 모든 sub_action_id가 동일한 개수의 계정을 가지고 있는지 검증
                long uniqueAccountCount = actions.stream()
                    .map(subAction -> accountMap.get(subAction.getSubActionId().toString()))
                    .filter(Objects::nonNull)
                    .mapToInt(List::size)
                    .distinct()
                    .count();

                if (uniqueAccountCount > 1) {
                    throw new RuntimeException("⚠️ 실행 순서별 계정 개수 불일치 (execute_order: " + executeOrder + ")");
                }

                // ✅ 계정 개수만큼 실행 (각 계정으로 실행)
                List<RpaAccount> accountList = accountMap.values().stream().findFirst().orElse(Collections.emptyList());
                for (RpaAccount account : accountList) {

                    // ✅ 계정별로 TagUI 스크립트 생성
                    String scriptName = systemId + "_" + executeOrder + "_" + account.getAccountId() + ".tag";
                    String scriptPath = BASE_SCRIPT_PATH + scriptName;

                    Files.createDirectories(Paths.get(BASE_SCRIPT_PATH));

                    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(scriptPath), StandardOpenOption.CREATE)) {
                        for (RpaSubAction subAction : actions) {
                            String replacedScript = subAction.getScriptContent()
                                .replace("{id}", account.getUsername())
                                .replace("{password}", account.getPassword());

                            writer.write(replacedScript);
                            writer.newLine();
                        }
                    }

                    // ✅ 계정별 TagUI 실행
                    if (!runTagUiScript(scriptPath)) {
                        return "❌ 실행 실패 (Order: " + executeOrder + " | Account: " + account.getUsername() + ")";
                    }
                }
            }

            return "✅ RPA 실행 완료: " + actionId;

        } catch (Exception e) {
            return "❌ 오류 발생: " + e.getMessage();
        }
    }


    private boolean runTagUiScript(String scriptPath) {
        try {
            logger.info("🚀 실행 시작: " + scriptPath);

			/*
			 * ProcessBuilder processBuilder = new ProcessBuilder( TAGUI_EXECUTABLE,
			 * scriptPath, "-headless", "-nobrowser", "-noverify", "-debug",
			 * "--disable-gpu", "--no-sandbox" );
			 */
            
            ProcessBuilder processBuilder = new ProcessBuilder(
                    TAGUI_EXECUTABLE, scriptPath, "-noverify", "-debug",
                    "--disable-gpu", "--no-sandbox"
                );
            processBuilder.directory(new File(BASE_SCRIPT_PATH));
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();
            logger.info("🔎 TagUI 실행 PID: " + process.pid());

            // ✅ 로그를 비동기로 읽는 스레드 실행
            Thread logReaderThread = new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                    String line;
                    while (process.isAlive() || reader.ready()) {
                        if ((line = reader.readLine()) != null) {
                            logger.info("[TAGUI] " + new String(line.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
                        } else {
                            Thread.sleep(50);
                        }
                    }
                } catch (Exception e) {
                    logger.warning("⚠️ 로그 읽기 중 오류 발생: " + e.getMessage());
                }
            });

            logReaderThread.start();

            // ✅ 실행 완료 대기
            int exitCode = process.waitFor();
            logger.info("✅ TagUI 실행 완료 (Exit Code: " + exitCode + ")");

            logReaderThread.interrupt();
            logReaderThread.join(1000);

            logger.info("✅ 로그 읽기 스레드 종료 완료");
            return exitCode == 0;

        } catch (Exception e) {
            logger.severe("❌ RPA 실행 중 오류 발생: " + e.getMessage());
            return false;
        }
    }
}
