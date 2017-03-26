package com.jdm.http.webserver.model;

import java.io.Serializable;

public class Permission implements Serializable {

	private static final long serialVersionUID = -6841193112007679056L;
	
	private String permission;

    public Permission(String permission) {
        this.permission = permission.toUpperCase();
    }

    @Override
    public String toString() {
        return this.permission;
    }

    public boolean equals(Permission rolePermission) {
        return this.permission.equals(rolePermission.permission);
    }
}
