package com.oi.dto;

import java.util.List;

public class MarketDTO {
    private long goodsListNum;
    private int categoryNum;
    private String memberId;
    private int postCateNum;
    private String goodsName;
    private int goodsPrice;
    private String goodsExp;
    private int goodsHitCnt;
    private String goodsDate;
    private int goodsBlind;

    private  String profilePhoto;
    private Marketfile file;

    private long fileNum;
    private String imageFilename;
    private List<String> imageFiles;

    public long getFileNum() {
        return fileNum;
    }

    public void setFileNum(long fileNum) {
        this.fileNum = fileNum;
    }

    public String getImageFilename() {
        return imageFilename;
    }

    public void setImageFilename(String imageFilename) {
        this.imageFilename = imageFilename;
    }

    public List<String> getImageFiles() {
        return imageFiles;
    }

    public void setImageFiles(List<String> imageFiles) {
        this.imageFiles = imageFiles;
    }

    public String getProfilePhoto() {
        if (file != null && file.getSaveFileName() != null && file.getSaveFileName().length > 0) {
            return file.getFile().get(0); // 첫 번째 파일 반환
        }
        return null; // 데이터가 없을 경우 null 반환
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public Marketfile getFile() {
        return file;
    }

    public void setFile(Marketfile file) {
        this.file = file;
    }

    public int getLoved() {
        return loved;
    }

    public void setLoved(int loved) {
        this.loved = loved;
    }

    public int getCommentcount() {
        return commentcount;
    }

    public void setCommentcount(int commentcount) {
        this.commentcount = commentcount;
    }

    private  int loved;
    private  int commentcount;


    public MarketDTO(){
        Marketfile file = new Marketfile();
        this.file = file;
    }


    public int getGoodsBlind() {
        return goodsBlind;
    }

    public void setGoodsBlind(int goodsBlind) {
        this.goodsBlind = goodsBlind;
    }

    public String getGoodsDate() {
        return goodsDate;
    }

    public void setGoodsDate(String goodsDate) {
        this.goodsDate = goodsDate;
    }

    public long getGoodsListNum() {
        return goodsListNum;
    }

    public void setGoodsListNum(long goodsListNum) {
        this.goodsListNum = goodsListNum;
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

    public String getGoodsExp() {
        return goodsExp;
    }

    public void setGoodsExp(String goodsExp) {
        this.goodsExp = goodsExp;
    }


    public int getGoodsHitCnt() {
        return goodsHitCnt;
    }

    public void setGoodsHitCnt(int goodsHitCnt) {
        this.goodsHitCnt = goodsHitCnt;
    }
}
