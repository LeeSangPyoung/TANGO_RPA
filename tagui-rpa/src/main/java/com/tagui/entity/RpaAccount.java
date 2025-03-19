package com.tagui.entity;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class RpaAccount {
    private String accountId;  // ACC000001 자동 생성
    private String systemId;
    private String username;
    private String password;
    private String regUserId;
    private LocalDateTime regDate;
    private String lastChgUserId;
    private LocalDateTime lastChgDate;
    // 🆕 추가: 서브 액션 ID (매핑용)
    private String subActionId;
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getSubActionId() {
		return subActionId;
	}
	public void setSubActionId(String subActionId) {
		this.subActionId = subActionId;
	}
    
    
}
