package com.oi.dto;

import java.sql.Date;
import java.util.List;

import com.oi.util.MyMultipartFile;

public class NoticeDTO {
	
	private long noticeNum;
	private String memberId;
	private Date noticeWriteDate;
	private Date noticeUpdateDate;
	private String noticePhoto;
	private String noticeTitle;
	private String noticeContent;
	private int notice;
	
	private long noticeFileNum;
	private String noticeSaveFileName;
	private String noticeOriFileName;
	
	private List<MyMultipartFile> listFile;

	public long getNoticeNum() {
		return noticeNum;
	}

	public void setNoticeNum(long noticeNum) {
		this.noticeNum = noticeNum;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Date getNoticeWriteDate() {
		return noticeWriteDate;
	}

	public void setNoticeWriteDate(Date noticeWriteDate) {
		this.noticeWriteDate = noticeWriteDate;
	}

	public Date getNoticeUpdateDate() {
		return noticeUpdateDate;
	}

	public void setNoticeUpdateDate(Date noticeUpdateDate) {
		this.noticeUpdateDate = noticeUpdateDate;
	}

	public String getNoticePhoto() {
		return noticePhoto;
	}

	public void setNoticePhoto(String noticePhoto) {
		this.noticePhoto = noticePhoto;
	}

	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public long getNoticeFileNum() {
		return noticeFileNum;
	}

	public void setNoticeFileNum(long noticeFileNum) {
		this.noticeFileNum = noticeFileNum;
	}

	public String getNoticeSaveFileName() {
		return noticeSaveFileName;
	}

	public void setNoticeSaveFileName(String noticeSaveFileName) {
		this.noticeSaveFileName = noticeSaveFileName;
	}

	public String getNoticeOriFileName() {
		return noticeOriFileName;
	}

	public void setNoticeOriFileName(String noticeOriFileName) {
		this.noticeOriFileName = noticeOriFileName;
	}

	public List<MyMultipartFile> getListFile() {
		return listFile;
	}

	public void setListFile(List<MyMultipartFile> listFile) {
		this.listFile = listFile;
	}

	public int getNotice() {
		return notice;
	}

	public void setNotice(int notice) {
		this.notice = notice;
	}

}
