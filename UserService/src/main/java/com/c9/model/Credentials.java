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
public class Credentials {
	 private Password password;
	 private Recovery_question recovery_question;
	 
	public Password getPassword() {
		return password;
	}

	public void setPassword(Password password) {
		this.password = password;
	}

	public Recovery_question getRecovery_question() {
		return recovery_question;
	}

	public void setRecovery_question(Recovery_question recovery_question) {
		this.recovery_question = recovery_question;
	}

	@Override
	public String toString() {
		return "Credentials [password=" + password + ", recovery_question=" + recovery_question + "]";
	}
	

}
