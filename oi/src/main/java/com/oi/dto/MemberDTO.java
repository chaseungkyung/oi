package com.oi.dto;

import java.sql.Date;

public class MemberDTO {
	
	private String memberId;
	private String memberPw;
	private String nickName;
	private Date memberSignUp;
	private int longinOk; 
	private int userLevel;
	private MemberDetailsDTO memberDetails;
	private BodyRecordDTO bodyRecord;
	
	// 의존성 주입
	public MemberDetailsDTO getMemberDetails() {
		return memberDetails;
	}
	public void setMemberDetails(MemberDetailsDTO memberDetails) {
		this.memberDetails = memberDetails;
	}
	public BodyRecordDTO getBodyRecord() {
		return bodyRecord;
	}
	public void setBodyRecord(BodyRecordDTO bodyRecord) {
		this.bodyRecord = bodyRecord;
	}
	
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberPw() {
		return memberPw;
	}
	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Date getMemberSignUp() {
		return memberSignUp;
	}
	public void setMemberSignUp(Date memberSignUp) {
		this.memberSignUp = memberSignUp;
	}
	public int getLonginOk() {
		return longinOk;
	}
	public void setLonginOk(int longinOk) {
		this.longinOk = longinOk;
	}
	public int getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(int userLevel) {
		this.userLevel = userLevel;
	}
	
	
	
}
