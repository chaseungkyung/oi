package com.oi.dto;

public class MarketDTO {
    private long goodsListNuum;
    private int categoryNum;
    private String memberId;
    private int postCateNum;
    private String goodsName;
    private int goodsPrice;
    private String goodsImage;
    private String goodsPhoto;
    private String goodsExp;
    private int goodsLikeCnt;
    private int goodsHitCnt;

    public long getGoodsListNuum() {
        return goodsListNuum;
    }

    public void setGoodsListNuum(long goodsListNuum) {
        this.goodsListNuum = goodsListNuum;
    }

    public int getCategoryNum() {
        return categoryNum;
    }

    public void setCategoryNum(int categoryNum) {
        this.categoryNum = categoryNum;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public int getPostCateNum() {
        return postCateNum;
    }

    public void setPostCateNum(int postCateNum) {
        this.postCateNum = postCateNum;
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

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public String getGoodsPhoto() {
        return goodsPhoto;
    }

    public void setGoodsPhoto(String goodsPhoto) {
        this.goodsPhoto = goodsPhoto;
    }

    public String getGoodsExp() {
        return goodsExp;
    }

    public void setGoodsExp(String goodsExp) {
        this.goodsExp = goodsExp;
    }

    public int getGoodsLikeCnt() {
        return goodsLikeCnt;
    }

    public void setGoodsLikeCnt(int goodsLikeCnt) {
        this.goodsLikeCnt = goodsLikeCnt;
    }

    public int getGoodsHitCnt() {
        return goodsHitCnt;
    }

    public void setGoodsHitCnt(int goodsHitCnt) {
        this.goodsHitCnt = goodsHitCnt;
    }
}
