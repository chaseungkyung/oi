package com.oi.dto;

public class LoginDTO {
	private String userId;
	private String nickname;
	private String userLevel;
	private String saveprofile;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}
	public String getSaveprofile() {
		return saveprofile;
	}
	public void setSaveprofile(String saveprofile) {
		this.saveprofile = saveprofile;
	}
	
}
