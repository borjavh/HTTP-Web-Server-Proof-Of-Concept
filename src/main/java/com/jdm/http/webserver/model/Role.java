package com.jdm.http.webserver.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Role  implements Serializable {
	
	private static final long serialVersionUID = 8764080177338747049L;

	private String name;
    private Set<Permission> permissions = new HashSet<Permission>();

    public Role(String name, Set<Permission> permissions) {
        this.name = name;
        this.permissions = permissions;
    }

    public String getName() {
        return name;
    }

    public boolean hasPermission(Permission rolePermission) {
        for (Permission permission:permissions) {
            if (permission.equals(rolePermission)) {
                return true;
            }
        }

        return false;
    }

    public ArrayList<Permission> getPermissions() {
        ArrayList<Permission> permissions = new ArrayList<Permission>();
        for (Permission permission:this.permissions) {
            permissions.add(permission);
        }

        return permissions;
    }
}
