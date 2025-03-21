package com.tagui.entity;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;


public class RpaAction {
    private String actionId;
    private String systemId;
    private String actionName;
    private List<RpaStep> steps;

    // Getters and Setters
    public String getActionId() { return actionId; }
    public void setActionId(String actionId) { this.actionId = actionId; }

    public String getSystemId() { return systemId; }
    public void setSystemId(String systemId) { this.systemId = systemId; }

    public String getActionName() { return actionName; }
    public void setActionName(String actionName) { this.actionName = actionName; }

    public List<RpaStep> getSteps() { return steps; }
    public void setSteps(List<RpaStep> steps) { this.steps = steps; }
}
