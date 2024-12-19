package com.oi.dto;

public class ReplyDTO {
 private long gcNum;
 private long num;
    private String userId;
    private String userName;
    private String content;
    private String reg_date;
    private long parentNum;

    private int showReply;
    private int block;

    private int answerCount;



    public long getGcNum() {
        return gcNum;
    }

    public void setGcNum(long gcNum) {
        this.gcNum = gcNum;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public long getParentNum() {
        return parentNum;
    }

    public void setParentNum(long parentNum) {
        this.parentNum = parentNum;
    }

    public int getShowReply() {
        return showReply;
    }

    public void setShowReply(int showReply) {
        this.showReply = showReply;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }


}
