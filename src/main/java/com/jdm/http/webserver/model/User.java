package com.jdm.http.webserver.model;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {

	private static final long serialVersionUID = -3780327461376841741L;

	private String username;
	private Password password;
	private Role role;
	private String id;
	
	public User(String username, Password password, Role role) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.id = UUID.randomUUID().toString(); // random ID.
	}

	public String getId() {
		return this.id;
	}
	
	public String getUsername() {
		return this.username;
	}

	public Password getPassword() {
		return this.password;
	}

	public String getRoleName() {
		return this.role.getName();
	}
	
	public boolean hasPermission(Permission permission) {
		return this.role.hasPermission(permission);
	}
}
