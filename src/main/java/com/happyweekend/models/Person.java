package com.happyweekend.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class Person {
	private Integer id = 0;
	private String firstName = "";
	private String lastName = "";
	private String email = "";
	private Date age = new Date();
	private Integer personTypeId = 0;
	private Login login = new Login();

	@Getter@Setter
	private Image image;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getAge() {
		return age;
	}
	public void setAge(Date age) {
		this.age = age;
	}
	public Integer getPersonTypeId() {
		return personTypeId;
	}
	public void setPersonTypeId(Integer personTypeId) {
		this.personTypeId = personTypeId;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}
}
