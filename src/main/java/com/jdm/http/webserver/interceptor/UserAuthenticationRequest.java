package com.jdm.http.webserver.interceptor;

import java.io.Serializable;

public class UserAuthenticationRequest implements Serializable{
	
	private static final long serialVersionUID = 7864851194251645903L;

	private String username;
    
    private byte[] password;
    
    public UserAuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password.getBytes();
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public byte[] getPassword() {
        return this.password;
    }
}
