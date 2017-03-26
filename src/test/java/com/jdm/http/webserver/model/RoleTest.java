package com.jdm.http.webserver.model;

import junit.framework.TestCase;
import com.jdm.http.webserver.model.Role;
import com.jdm.http.webserver.model.Permission;

import java.util.HashSet;

public class RoleTest extends TestCase {
	
	public static final String WRITE = "WRITE";
	private static final String READ = "READ";
	public static final String ROLE_NAME = "ROLE_NAME";
	private Role role;
	private HashSet<Permission> permissions;

	@Override
	protected void setUp() throws Exception {
		permissions = new HashSet<Permission>();
		permissions.add(new Permission(WRITE));
		role = new Role(ROLE_NAME, permissions);
	}

	public void testGetRoleName() {
		assertSame(ROLE_NAME, role.getName());
	}

	public void testHasPermission() {
		assertTrue(role.hasPermission(new Permission(WRITE)));
		assertFalse(role.hasPermission(new Permission(READ)));
	}

	public void testGetPermissions() {
		assertSame(permissions.size(), role.getPermissions().size());
	}
}
