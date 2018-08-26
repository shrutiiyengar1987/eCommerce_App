package com.c9.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.c9.model.Credentials;
import com.c9.model.Profile;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/*@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)*/
//@JsonInclude
public class AddUserInfo {
	 private Profile profile;
	 private Credentials credentials;
	 
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	@Override
	public String toString() {
		return "AddUserInfo [profile=" + profile + ", credentials=" + credentials + "]";
	}
	


}
