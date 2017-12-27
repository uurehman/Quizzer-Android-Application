package com.ap.quizzer.Model;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class User{

	@Id
	private String username;
	private String password;
	private boolean role; // Role can be instructor or Student. Instructor => false ,
	// Student => true

	public User(){

	}

	public User(String username, String password, boolean role) {
		this.setUsername(username);
		this.setPassword(password);
		this.role = role;
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

	public boolean getRole() {
		return role;
	}

	public void setRole(boolean role) {
		this.role = role;
	}

}
