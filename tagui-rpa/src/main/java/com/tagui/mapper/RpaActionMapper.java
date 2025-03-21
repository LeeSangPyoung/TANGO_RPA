package com.tagui.mapper;

import com.tagui.entity.RpaAccount;
import com.tagui.entity.RpaAction;
import com.tagui.entity.RpaStep;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface RpaActionMapper {

    // ✅ 특정 action_id로 실행할 액션 조회
    RpaAction findActionByActionId(@Param("actionId") String actionId);

    // ✅ 시스템 ID로 도메인 조회
    String findDomainBySystemId(@Param("systemId") String systemId);

    // ✅ 특정 action_id에 대한 단계 목록 조회
    List<RpaStep> findStepsByActionId(@Param("actionId") String actionId);
    
    // ✅ 특정 단계에 매핑된 계정 조회
    List<RpaAccount> findAccountsByStepId(@Param("stepId") String stepId);
    
    // ✅ 특정 actionId에 매핑된 계정 조회
    List<RpaAccount> findAccountsByActionId(@Param("actionId") String actionId);
    
    // ✅ 실행 순서별 개수 조회 (실행 정합성 확인)
    List<Map<String, Object>> countStepsByExecuteOrder(@Param("actionId") String actionId);
}