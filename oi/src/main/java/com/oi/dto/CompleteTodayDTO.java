package com.oi.dto;


public class CompleteTodayDTO {
	private long wnum;
	private int category;		// 10
	private String memberId;
	private String nickName;
	private String content;
	private String date;
	private String updatedate;
	private boolean blind;
	private wotdfile file;
	
	public CompleteTodayDTO() {
		wotdfile file = new wotdfile();
		this.file = file;
	}
	public long getWnum() {
		return wnum;
	}
	public void setWnum(long wnum) {
		this.wnum = wnum;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}
	public boolean isBlind() {
		return blind;
	}
	public void setBlind(int number) {
		boolean blind;
		if(number == 0 ) {
			blind = false;
		}else {
			blind = true;
		}
		this.blind = blind;
	}
	public wotdfile getFile() {
		return file;
	}
	public void setFile(wotdfile file) {
		this.file = file;
	}
	
}