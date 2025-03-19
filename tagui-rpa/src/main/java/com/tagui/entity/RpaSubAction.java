package com.tagui.entity;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class RpaSubAction {
    private String subActionId;  // SUB000001 자동 생성
    private String actionId;
    private String subActionName;
    private String scriptContent;  // TAGUI 스크립트 저장
    private int executeOrder;
    private String regUserId;
    private LocalDateTime regDate;
    private String lastChgUserId;
    private LocalDateTime lastChgDate;
    private String useYn; // ✅ 실행 여부 (Y/N)
    public String getSubActionId() {
		return subActionId;
	}
	public void setSubActionId(String subActionId) {
		this.subActionId = subActionId;
	}
	public String getActionId() {
		return actionId;
	}
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}
	public String getSubActionName() {
		return subActionName;
	}
	public void setSubActionName(String subActionName) {
		this.subActionName = subActionName;
	}
	public String getScriptContent() {
		return scriptContent;
	}
	public void setScriptContent(String scriptContent) {
		this.scriptContent = scriptContent;
	}
	public int getExecuteOrder() {
		return executeOrder;
	}
	public void setExecuteOrder(int executeOrder) {
		this.executeOrder = executeOrder;
	}
	public String getRegUserId() {
		return regUserId;
	}
	public void setRegUserId(String regUserId) {
		this.regUserId = regUserId;
	}
	public LocalDateTime getRegDate() {
		return regDate;
	}
	public void setRegDate(LocalDateTime regDate) {
		this.regDate = regDate;
	}
	public String getLastChgUserId() {
		return lastChgUserId;
	}
	public void setLastChgUserId(String lastChgUserId) {
		this.lastChgUserId = lastChgUserId;
	}
	public LocalDateTime getLastChgDate() {
		return lastChgDate;
	}
	public void setLastChgDate(LocalDateTime lastChgDate) {
		this.lastChgDate = lastChgDate;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
    
    
}

