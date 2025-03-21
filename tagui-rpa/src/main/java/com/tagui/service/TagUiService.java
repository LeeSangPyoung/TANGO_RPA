package com.tagui.service;

import com.tagui.entity.RpaAction;
import com.tagui.entity.RpaStep;
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
            logger.info("abcd : " + rpaAction.toString());
            if (rpaAction == null) {
                return "❌ 해당 액션 ID에 대한 정보 없음";
            }


            List<RpaStep> steps = actionMapper.findStepsByActionId(actionId);
            
            
            steps.get(0).getSiteUrl();
            String siteId = steps.get(0).getSiteId();
            Map<Integer, List<RpaStep>> groupedSteps = steps.stream()
                .collect(Collectors.groupingBy(RpaStep::getExecuteOrder));

            Map<String, List<RpaAccount>> accountMap = actionMapper.findAccountsByActionId(actionId).stream()
                .collect(Collectors.groupingBy(account -> account.getStepId().toString()));

            for (Map.Entry<Integer, List<RpaStep>> entry : groupedSteps.entrySet()) {
                int executeOrder = entry.getKey();
                List<RpaStep> stepList = entry.getValue();

                List<RpaAccount> accountList = accountMap.values().stream().findFirst().orElse(Collections.emptyList());
                
                for (RpaAccount account : accountList) {
                    // ✅ 실행 시각 기준 폴더명
                    String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

                    // ✅ 파일명 및 디렉토리 구조
                    String scriptFileName = siteId + "_" + executeOrder + "_" + account.getAccountId() + ".tag";
                    String scriptDirPath = BASE_SCRIPT_PATH + actionId + "\\" + timestamp + "\\";
                    String scriptPath = scriptDirPath + scriptFileName;

                    // ✅ 디렉토리 생성
                    Files.createDirectories(Paths.get(scriptDirPath));

                    // ✅ 스크립트 파일 생성
                    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(scriptPath), StandardOpenOption.CREATE)) {

                        // ✅ 맨 앞에 열어줄 site가 있다면 처리
                        Optional<String> firstUrl = stepList.stream()
                            .filter(step -> step.getSiteUrl() != null && !step.getSiteUrl().isEmpty())
                            .map(RpaStep::getSiteUrl)
                            .findFirst();

                        if (firstUrl.isPresent()) {
                            writer.write(firstUrl.get());
                            writer.newLine();
                            writer.write("wait 2");
                            writer.newLine();
                        }

                        // ✅ 스크립트 내용 작성
                        for (RpaStep step : stepList) {
                            String replacedScript = step.getScriptContent()
                                .replace("{id}", account.getUsername())
                                .replace("{password}", account.getPassword());

                            writer.write(replacedScript);
                            writer.newLine();
                        }
                    }

                    // ✅ 실행
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


            ProcessBuilder processBuilder = new ProcessBuilder(
            	    TAGUI_EXECUTABLE, scriptPath,
            	    "-headless",    // ✅ 하이픈 붙이기!
            	    "-noverify",
            	    "-debug",
            	    "--disable-gpu",
            	    "--no-sandbox"
            	);
            processBuilder.directory(new File(BASE_SCRIPT_PATH));
            processBuilder.redirectErrorStream(true);
            processBuilder.environment().put("PYTHONIOENCODING", "utf-8");
            processBuilder.environment().put("JAVA_TOOL_OPTIONS", "-Dfile.encoding=UTF-8");


            Process process = processBuilder.start();
            logger.info("🔎 TagUI 실행 PID: " + process.pid());

            Thread logReaderThread = new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(
                	    new InputStreamReader(process.getInputStream(), "MS949")  // 🔁 여기만 바꿔도 한글 깨짐 방지
                		)) {
                    String line;
                    while (process.isAlive() || reader.ready()) {
                        if ((line = reader.readLine()) != null) {
                            logger.info("[TAGUI] " + line);
                        } else {
                            Thread.sleep(50);
                        }
                    }
                } catch (Exception e) {
                    logger.warning("⚠️ 로그 읽기 중 오류 발생: " + e.getMessage());
                }
            });

            logReaderThread.start();

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
