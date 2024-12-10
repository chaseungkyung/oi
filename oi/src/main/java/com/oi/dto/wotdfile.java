package com.oi.dto;

import java.util.List;

import com.oi.util.MyMultipartFile;

public class wotdfile {
	private List<MyMultipartFile> filenames ;
	private long [] photoNum;
	private String [] saveFileName;
	public List<MyMultipartFile> getFilenames() {
		return filenames;
	}
	public void setFilenames(List<MyMultipartFile> filenames) {
		this.filenames = filenames;
	}
	public long[] getPhotoNum() {
		return photoNum;
	}
	public void setPhotoNum(long[] photoNum) {
		this.photoNum = photoNum;
	}
	public String[] getSaveFileName() {
		return saveFileName;
	}
	public void setSaveFileName(String[] saveFileName) {
		this.saveFileName = saveFileName;
	}
	
}
