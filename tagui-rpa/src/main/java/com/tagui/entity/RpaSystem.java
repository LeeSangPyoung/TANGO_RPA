package com.tagui.entity;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class RpaSystem {
    private String systemId;  // SYS000001 자동 생성
    private String systemName;
    private String domainUrl;
    private String regUserId;
    private LocalDateTime regDate;
    private String lastChgUserId;
    private LocalDateTime lastChgDate;
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public String getDomainUrl() {
		return domainUrl;
	}
	public void setDomainUrl(String domainUrl) {
		this.domainUrl = domainUrl;
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
    
}
