package com.oi.dto;

public class MemberDetailsDTO {
	private String birth; // 날짜 연산 할 일이 없어서 String으로 설정함
	private String email;
	private String address;
	private String addressNum;
	private String name;
	private String profilePhoto;
	
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddressNum() {
		return addressNum;
	}
	public void setAddressNum(String addressNum) {
		this.addressNum = addressNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProfilePhoto() {
		return profilePhoto;
	}
	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}
}
