package com.jdm.http.webserver.interceptor;

import java.io.Serializable;

import com.jdm.http.webserver.model.User;

public class UserAuthenticationResponse implements Serializable {

	private static final long serialVersionUID = -1999232541074646587L;

	private boolean authenticated = false;

	private User user;

	public UserAuthenticationResponse(User user) {
		if (user != null) {
			this.authenticated = true;
			this.user = user;
		}
	}

	public boolean isAuthenticated() {
		return this.authenticated;
	}

	public User getUser() {
		return this.user;
	}
}
