package com.oi.dto;

import java.time.LocalDate;

public class BodyRecordDTO {
	private String gender;
	private int height;
	private int weight;
	private LocalDate bodyRecordDate;
	private int bodyFat;
	private int bodyMuscle;
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public LocalDate getBodyRecordDate() {
		return bodyRecordDate;
	}
	public void setBodyRecordDate(LocalDate bodyRecordDate) {
		this.bodyRecordDate = bodyRecordDate;
	}
	public int getBodyFat() {
		return bodyFat;
	}
	public void setBodyFat(int bodyFat) {
		this.bodyFat = bodyFat;
	}
	public int getBodyMuscle() {
		return bodyMuscle;
	}
	public void setBodyMuscle(int bodyMuscle) {
		this.bodyMuscle = bodyMuscle;
	}
}
