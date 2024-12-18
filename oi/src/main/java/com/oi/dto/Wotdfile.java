package com.oi.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oi.util.MyMultipartFile;

public class Wotdfile {
	private List<MyMultipartFile> filenames;
	private Map<Long, String> file = new HashMap<Long, String>();
	private List<String> list = new ArrayList<String>();
	private String [] saveFileName;
	private long parentnum;
	
	public List<MyMultipartFile> getFilenames() {
		return filenames;
	}
	public void setFilenames(List<MyMultipartFile> filenames) {
		this.filenames = filenames;
	}
	public String[] getSaveFileName() {
		return saveFileName;
	}
	public void setSaveFileName(String[] saveFileName) {
		this.saveFileName = saveFileName;
	}
	public Map<Long, String> getFile() {
		return file;
	}
	public void setFile(long num, String name) {
		this.file.put(num, name);
	}
	public long getParentnum() {
		return parentnum;
	}
	public void setParentnum(long parentnum) {
		this.parentnum = parentnum;
	}
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	
}
