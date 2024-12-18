package com.oi.dto;

public class FaqDTO {
	
	private long faqNum;
	private String faqTitle;
	private String faqContent;
	private String memberId;
	
	private String faqCateName;
	private long faqCateNum;
	private int enabled;
	private int orderNo;

	public long getFaqNum() {
		return faqNum;
	}
	public void setFaqNum(long faqNum) {
		this.faqNum = faqNum;
	}
	public String getFaqTitle() {
		return faqTitle;
	}
	public void setFaqTitle(String faqTitle) {
		this.faqTitle = faqTitle;
	}
	public String getFaqContent() {
		return faqContent;
	}
	public void setFaqContent(String faqContent) {
		this.faqContent = faqContent;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getFaqCateName() {
		return faqCateName;
	}
	public void setFaqCateName(String faqCateName) {
		this.faqCateName = faqCateName;
	}
	public long getFaqCateNum() {
		return faqCateNum;
	}
	public void setFaqCateNum(long faqCateNum) {
		this.faqCateNum = faqCateNum;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
}
