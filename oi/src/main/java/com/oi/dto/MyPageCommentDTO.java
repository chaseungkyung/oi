package com.oi.dto;

public class MyPageCommentDTO {
    private String memberId;       // 회원 ID
    private String commentContent; // 댓글 내용
    private String commentDate; // 댓글 작성 날짜
    private String postTitle;      // 댓글이 작성된 게시물 제목 (또는 이동 링크)
    private String goodsName;
    private int goodsListNum;   // 중고거래 게시물 번호
    
    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

	public int getGoodsListNum() {
		return goodsListNum;
	}

	public void setGoodsListNum(int goodsListNum) {
		this.goodsListNum = goodsListNum;
	}
}