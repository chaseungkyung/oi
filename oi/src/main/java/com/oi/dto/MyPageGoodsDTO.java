package com.oi.dto;

public class MyPageGoodsDTO {
    private int goodsListNum;
    private String goodsName;
    private int goodsPrice;
    private String goodsExp;
    private String goodsDate;
    private String goodsMemberId;
    private String categoryName;
    private String postCateName;
   
    private int gcNum;              // 댓글 번호
    private String commentMemberId; // 댓글 작성자 ID
    private String gcComCon;        // 댓글 내용
    private String gcInsertNum;     // 댓글 작성일

    public int getGoodsListNum() {
        return goodsListNum;
    }

    public void setGoodsListNum(int goodsListNum) {
        this.goodsListNum = goodsListNum;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(int goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsExp() {
        return goodsExp;
    }

    public void setGoodsExp(String goodsExp) {
        this.goodsExp = goodsExp;
    }

    public String getGoodsDate() {
        return goodsDate;
    }

    public void setGoodsDate(String goodsDate) {
        this.goodsDate = goodsDate;
    }

    // 댓글 관련 Getter/Setter
    public int getGcNum() {
        return gcNum;
    }

    public void setGcNum(int gcNum) {
        this.gcNum = gcNum;
    }

    public String getCommentMemberId() {
        return commentMemberId;
    }

    public void setCommentMemberId(String commentMemberId) {
        this.commentMemberId = commentMemberId;
    }

    public String getGcComCon() {
        return gcComCon;
    }

    public void setGcComCon(String gcComCon) {
        this.gcComCon = gcComCon;
    }

    public String getGcInsertNum() {
        return gcInsertNum;
    }

    public void setGcInsertNum(String gcInsertNum) {
        this.gcInsertNum = gcInsertNum;
    }

	public String getGoodsMemberId() {
		return goodsMemberId;
	}

	public void setGoodsMemberId(String goodsMemberId) {
		this.goodsMemberId = goodsMemberId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getPostCateName() {
		return postCateName;
	}

	public void setPostCateName(String postCateName) {
		this.postCateName = postCateName;
	}
}