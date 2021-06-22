package com.springtutorial.springsecurityjpa.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "invalidJwt")
@Table(name = "invalid_jwt")
public class InvalidJwt {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "jwt")
	private String jwt;

	public InvalidJwt() {
		super();
	}
	
	public InvalidJwt(String jwt) {
		this.jwt = jwt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	@Override
	public String toString() {
		return "InvalidJwt [id=" + id + ", jwt=" + jwt + "]";
	}

	
}
