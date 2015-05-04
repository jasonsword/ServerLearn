package com.meteor.entity;

public class User {
	
	private int index;
	private String username;
	private String password;
	private String name;
	private int sex;
	private int age;
	private String telephone;
	private String idcard;
	private boolean locked;
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	@Override
	public String toString() {
		return "User [index=" + index + ", username=" + username
				+ ", password=" + password + ", name=" + name + ", sex=" + sex
				+ ", age=" + age + ", telephone=" + telephone + ", idcard="
				+ idcard + ", locked=" + locked + "]";
	}
}
