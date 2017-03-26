package com.jdm.http.webserver.model;

import junit.framework.TestCase;
import com.jdm.http.webserver.model.Permission;

public class PermissionTest extends TestCase {
	public static final String EDIT_PERMISSION = "EDIT";
	public static final String VIEW_PERMISSION = "VIEW";
	private Permission permission;

	@Override
	protected void setUp() throws Exception {
		permission = new Permission(EDIT_PERMISSION);
	}

	public void testGetRolePermissionName() {
		assertSame(EDIT_PERMISSION, permission.toString());
	}

	public void testEqualsOtherRolePermissions() {
		assertFalse(new Permission(VIEW_PERMISSION).equals(permission));
		assertTrue(new Permission(EDIT_PERMISSION).equals(permission));
	}
}
