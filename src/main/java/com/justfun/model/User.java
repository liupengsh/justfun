package com.justfun.model;

import java.io.Serializable;

public class User implements Serializable {
	
	private static final long serialVersionUID = -8113344948598181684L;
	
	private Long id;
	private String userName;
	private String password;
	private Integer age;
	private String salt;
	
	public String getCredentialsSalt() {
        return userName + salt;
    }
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
}
