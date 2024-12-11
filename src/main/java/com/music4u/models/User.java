package com.music4u.models;

public class User {
	Long id;
	String name;
	String email;
	String password;
	String role;
	Boolean enable;
	
	
	public User(Long id, String name, String email, String password, String role, Boolean enable) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.enable = enable;
	}
	
	public User( String name, String email, String password, String role, Boolean enable) {
		
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.enable = enable;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Boolean getEnable() {
		return enable;
	}
	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
	
	
	
	
	

}
