package com.oi.dto;

import com.oi.util.MyMultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Marketfile {
    private List<MyMultipartFile> filenames;
    private Map<Long,String> file = new HashMap<Long,String>();
    private String [] saveFileName;
    private long parentnum;

    public List<MyMultipartFile> getFilenames() {
        return filenames;
    }

    public void setFilenames(List<MyMultipartFile> filenames) {
        this.filenames = filenames;
    }

    public Map<Long, String> getFile() {
        return file;
    }

    public void setFile(long num,String name) {
        this.file = file;
    }

    public String[] getSaveFileName() {
        return saveFileName;
    }

    public void setSaveFileName(String[] saveFileName) {
        this.saveFileName = saveFileName;
    }

    public long getParentnum() {
        return parentnum;
    }

    public void setParentnum(long parentnum) {
        this.parentnum = parentnum;
    }
}
