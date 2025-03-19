package com.tagui.entity;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
public class RpaAction {
    private String actionId;  // ACT000001 자동 생성
    private String systemId;
    private String actionName;
    private int executeOrder;
    private String regUserId;
    private LocalDateTime regDate;
    private String lastChgUserId;
    private LocalDateTime lastChgDate;
    private List<RpaSubAction> subActions;
	public String getActionId() {
		return actionId;
	}
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
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
	public List<RpaSubAction> getSubActions() {
		return subActions;
	}
	public void setSubActions(List<RpaSubAction> subActions) {
		this.subActions = subActions;
	}
}

