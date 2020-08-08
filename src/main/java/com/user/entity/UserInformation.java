package com.user.entity;

public class UserInformation {
	private String id;
    private int gender;
    private int age;
    private String address;
    private int type;
    private String sysnopsis;
    private String shoppingAddress;
    private int level;
    private String achievements;
	public UserInformation(String id, int gender, int age, String address, int type, String sysnopsis, String iconPath,
			String shoppingAddress, int level, String achievements) {
		super();
		this.id = id;
		this.gender = gender;
		this.age = age;
		this.address = address;
		this.type = type;
		this.sysnopsis = sysnopsis;
		this.shoppingAddress = shoppingAddress;
		this.level = level;
		this.achievements = achievements;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getSysnopsis() {
		return sysnopsis;
	}
	public void setSysnopsis(String sysnopsis) {
		this.sysnopsis = sysnopsis;
	}
	public String getShoppingAddress() {
		return shoppingAddress;
	}
	public void setShoppingAddress(String shoppingAddress) {
		this.shoppingAddress = shoppingAddress;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getAchievements() {
		return achievements;
	}
	public void setAchievements(String achievements) {
		this.achievements = achievements;
	}
	@Override
	public String toString() {
		return "UserInformation [id=" + id + ", gender=" + gender + ", age=" + age + ", address=" + address + ", type="
				+ type + ", sysnopsis=" + sysnopsis + ", shoppingAddress=" + shoppingAddress
				+ ", level=" + level + ", achievements=" + achievements + "]";
	} 
}
