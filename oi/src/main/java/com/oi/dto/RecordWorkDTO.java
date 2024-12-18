package com.oi.dto;

public class RecordWorkDTO {
	private int exerciseNum; // 운동 기록 번호 (기본키)
	private String memberId; // 회원ID
	private String exerciseDate; // 운동날짜
	private String exerciseName; // 운동명
	private String exerciseContent; // 운동내용
	private String exerciseStart; // 운동시작시간
	private String exerciseEnd; // 운동종료시간
	private int exercisecnt; // 운동횟수
	private String exerciseunit; // 운동단위
	
	public int getExerciseNum() {
	    return exerciseNum;
	}
	public void setExerciseNum(int exerciseNum) {
	    this.exerciseNum = exerciseNum;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getExerciseDate() {
		return exerciseDate;
	}
	public void setExerciseDate(String exerciseDate) {
		this.exerciseDate = exerciseDate;
	}
	public String getExerciseName() {
		return exerciseName;
	}
	public void setExerciseName(String exerciseName) {
		this.exerciseName = exerciseName;
	}
	public String getExerciseContent() {
		return exerciseContent;
	}
	public void setExerciseContent(String exerciseContent) {
		this.exerciseContent = exerciseContent;
	}
	public String getExerciseStart() {
		return exerciseStart;
	}
	public void setExerciseStart(String exerciseStart) {
		this.exerciseStart = exerciseStart;
	}
	public String getExerciseEnd() {
		return exerciseEnd;
	}
	public void setExerciseEnd(String exerciseEnd) {
		this.exerciseEnd = exerciseEnd;
	}
	public int getExercisecnt() {
		return exercisecnt;
	}
	public void setExercisecnt(int exercisecnt) {
		this.exercisecnt = exercisecnt;
	}
	public String getExerciseunit() {
		return exerciseunit;
	}
	public void setExerciseunit(String exerciseunit) {
		this.exerciseunit = exerciseunit;
	}
}