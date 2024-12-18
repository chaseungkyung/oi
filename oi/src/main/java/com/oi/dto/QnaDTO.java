package com.oi.dto;

public class QnaDTO {
	private long questionNum;
	private String questionId;
	private String questionName;
	private String questionTitle;
	private String questionCon;
	private String questionDate;
	
	private long ansNum;
	private String answerId;
	public String getQuestionName() {
		return questionName;
	}
	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}
	public String getAnswerName() {
		return answerName;
	}
	public void setAnswerName(String answerName) {
		this.answerName = answerName;
	}
	private String answerName;
	private String ansTitle;
	private String ansContent;
	private String ansDate;
	public long getQuestionNum() {
		return questionNum;
	}
	public void setQuestionNum(long questionNum) {
		this.questionNum = questionNum;
	}
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getQuestionTitle() {
		return questionTitle;
	}
	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}
	public String getQuestionCon() {
		return questionCon;
	}
	public void setQuestionCon(String questionCon) {
		this.questionCon = questionCon;
	}
	public String getQuestionDate() {
		return questionDate;
	}
	public void setQuestionDate(String questionDate) {
		this.questionDate = questionDate;
	}
	public long getAnsNum() {
		return ansNum;
	}
	public void setAnsNum(long ansNum) {
		this.ansNum = ansNum;
	}
	public String getAnswerId() {
		return answerId;
	}
	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}
	public String getAnsTitle() {
		return ansTitle;
	}
	public void setAnsTitle(String ansTitle) {
		this.ansTitle = ansTitle;
	}
	public String getAnsContent() {
		return ansContent;
	}
	public void setAnsContent(String ansContent) {
		this.ansContent = ansContent;
	}
	public String getAnsDate() {
		return ansDate;
	}
	public void setAnsDate(String ansDate) {
		this.ansDate = ansDate;
	}
	
}
