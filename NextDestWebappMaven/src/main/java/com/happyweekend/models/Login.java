package com.happyweekend.models;

import org.apache.tomcat.util.security.MD5Encoder;

public class Login {
	private Integer id;
	private String loginName;
	private String password;
	
	public Login(String loginName, String password) {
		this.loginName = loginName;
		this.password = MD5Encoder.encode(password.getBytes());
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
