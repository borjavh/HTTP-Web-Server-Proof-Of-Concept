package com.jdm.http.webserver.server.session;


import java.io.Serializable;
import java.util.UUID;

import com.jdm.http.webserver.model.Permission;
import com.jdm.http.webserver.model.User;

public class Session implements Serializable {

	private static final long serialVersionUID = -682096595364869455L;

	private String id;
    private User user;

    public Session() {
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public boolean hasUser() {
        return this.user != null;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void logoutUser() {
        this.user = null;
    }
    
    public boolean hasPermission(String permission) {
        return hasUser() && getUser().hasPermission(new Permission(permission));
    }
}
