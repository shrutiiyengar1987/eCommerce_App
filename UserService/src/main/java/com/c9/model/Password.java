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
public class Password {
	 private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	 

}
