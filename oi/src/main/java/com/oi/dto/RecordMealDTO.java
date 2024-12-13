package com.oi.dto;

public class RecordMealDTO {

	private int dietFoodNum;
	private String memberId;
	private String dietFoodTime;
	private String dietFoodDate;
	private String dietFoodUnit;
	private String dietFoodName;
	private String capacity;
	private int kcal;
	
	private long memberIdx;
	private String allDay;
	private int repeat, repeat_cycle;
	private String reg_date;
	
	
	public int getDietFoodNum() {
		return dietFoodNum;
	}
	public void setDietFoodNum(int dietFoodNum) {
		this.dietFoodNum = dietFoodNum;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getDietFoodTime() {
		return dietFoodTime;
	}
	public void setDietFoodTime(String dietFoodTime) {
		this.dietFoodTime = dietFoodTime;
	}
	public String getDietFoodDate() {
		return dietFoodDate;
	}
	public void setDietFoodDate(String dietFoodDate) {
		this.dietFoodDate = dietFoodDate;
	}
	public String getDietFoodUnit() {
		return dietFoodUnit;
	}
	public void setDietFoodUnit(String dietFoodUnit) {
		this.dietFoodUnit = dietFoodUnit;
	}
	public String getDietFoodName() {
		return dietFoodName;
	}
	public void setDietFoodName(String dietFoodName) {
		this.dietFoodName = dietFoodName;
	}
	public String getCapacity() {
		return capacity;
	}
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	public int getKcal() {
		return kcal;
	}
	public void setKcal(int kcal) {
		this.kcal = kcal;
	}
	public long getMemberIdx() {
		return memberIdx;
	}
	public void setMemberIdx(long memberIdx) {
		this.memberIdx = memberIdx;
	}
	public String getAllDay() {
		return allDay;
	}
	public void setAllDay(String allDay) {
		this.allDay = allDay;
	}
	public int getRepeat() {
		return repeat;
	}
	public void setRepeat(int repeat) {
		this.repeat = repeat;
	}
	public int getRepeat_cycle() {
		return repeat_cycle;
	}
	public void setRepeat_cycle(int repeat_cycle) {
		this.repeat_cycle = repeat_cycle;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	
	
}
