package com.c9.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
/*@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)*/
//@JsonInclude
public class Profile {

 private String firstName,lastName,email,login,mobilePhone;
/* private String lastName;
 private String email;
 private String login;
 private String mobilePhone;*/
 

	@Override
	public String toString() {
		return "Profile [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", login=" + login
				+ ", mobilePhone=" + mobilePhone + "]";
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


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getMobilePhone() {
		return mobilePhone;
	}


	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
}
