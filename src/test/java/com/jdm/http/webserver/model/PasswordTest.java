package com.jdm.http.webserver.model;

import junit.framework.TestCase;
import com.jdm.http.webserver.model.Password;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class PasswordTest extends TestCase {

	public void testCreateDifferentPasswords() throws UnsupportedEncodingException {
		String password = "qwerty";
		byte[] passwordBytes = password.getBytes("UTF-8");
		Password aPassword = Password.createFromUncrypted(password);
		Password anotherPassword = Password.createPasswordFromNoCypheredBytes(passwordBytes);
		assertFalse(aPassword.isCiphered());
		assertFalse(anotherPassword.isCiphered());
		assertTrue(aPassword.equals(anotherPassword));
		assertTrue(Arrays.equals(passwordBytes, anotherPassword.getPassword()));
		assertTrue(Arrays.equals(passwordBytes, aPassword.getPassword()));
	}

	public void testCreateCypheredPassword() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		byte[] cryptedPasswordBytes = { -40, 87, -114, -33, -124, 88, -50, 6, -5, -59, -69, 118, -91, -116, 92, -92 };
		Password password = Password.createPasswordFromCypheredBytes(cryptedPasswordBytes);
		assertTrue(password.isCiphered());
		assertEquals(cryptedPasswordBytes, password.getPassword());
	}
}
