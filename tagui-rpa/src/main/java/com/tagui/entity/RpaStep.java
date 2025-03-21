package com.tagui.entity;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class RpaStep {
    private String stepId;
    private String actionId;
    private String siteId;
    private String siteUrl;
    private String stepType;
    private String scriptContent;
    private int executeOrder;
    private List<RpaAccount> accounts;

    // Getters and Setters
    public String getStepId() { return stepId; }
    public void setStepId(String stepId) { this.stepId = stepId; }

    public String getActionId() { return actionId; }
    public void setActionId(String actionId) { this.actionId = actionId; }

    public String getSiteId() { return siteId; }
    public void setSiteId(String siteId) { this.siteId = siteId; }

    public String getStepType() { return stepType; }
    public void setStepType(String stepType) { this.stepType = stepType; }

    public String getScriptContent() { return scriptContent; }
    public void setScriptContent(String scriptContent) { this.scriptContent = scriptContent; }

    public int getExecuteOrder() { return executeOrder; }
    public void setExecuteOrder(int executeOrder) { this.executeOrder = executeOrder; }

    public List<RpaAccount> getAccounts() { return accounts; }
    public void setAccounts(List<RpaAccount> accounts) { this.accounts = accounts; }
	public String getSiteUrl() {
		return siteUrl;
	}
	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}

    
}

