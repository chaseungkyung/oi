package com.oi.dto;


// 댓글객체
public class WotdCommentDTO {
	private long parent;
	private long commentseq;
	private String memberId;
	private String memberPhoto;
	private String innercontent;
	private String writtenDate;
	
	public String getMemberPhoto() {
		return memberPhoto;
	}
	public void setMemberPhoto(String memberPhoto) {
		this.memberPhoto = memberPhoto;
	}
	public long getParent() {
		return parent;
	}
	public void setParent(long parent) {
		this.parent = parent;
	}
	public long getCommentseq() {
		return commentseq;
	}
	public void setCommentseq(long commentseq) {
		this.commentseq = commentseq;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getInnercontent() {
		return innercontent;
	}
	public void setInnercontent(String innercontent) {
		this.innercontent = innercontent;
	}
	public String getWrittenDate() {
		return writtenDate;
	}
	public void setWrittenDate(String writtenDate) {
		this.writtenDate = writtenDate;
	}
	
}
