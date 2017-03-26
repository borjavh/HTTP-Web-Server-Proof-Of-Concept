package com.jdm.http.webserver.interceptor;

import junit.framework.TestCase;
import com.jdm.http.webserver.interceptor.UserAuthenticator;
import com.jdm.http.webserver.model.Password;
import com.jdm.http.webserver.model.User;
import com.jdm.http.webserver.repository.UserRepositoryImpl;

import java.io.UnsupportedEncodingException;

public class UserAuthenticatorTest extends TestCase {
	
	private UserAuthenticator userAuthenticator;

	public static final String VALID_USERNAME = "user_1";
	public static final String VALID_PASSWORD = "qwerty";
	private static final String INVALID_USERNAME = "user_7";
	private static final String INVALID_PASSWORD = "qwertu";

	@Override
	protected void setUp() throws Exception {
		userAuthenticator = new UserAuthenticator(new UserRepositoryImpl());
	}

	public void testAuthenticateValidUser() throws UnsupportedEncodingException {
		User user = userAuthenticator.
				authenticateUser(VALID_USERNAME, Password.createFromUncrypted(VALID_PASSWORD));
		assertNotNull(user);
		assertSame(VALID_USERNAME, user.getUsername());
	}

	public void testNoAuthenticateInvalidUser() throws UnsupportedEncodingException {
		User user = userAuthenticator.
				authenticateUser(INVALID_USERNAME, Password.createFromUncrypted(VALID_PASSWORD));
		assertNull(user);
	}

	public void testNoAuthenticateValidUserWithInvalidPassword() throws UnsupportedEncodingException {
		User user = userAuthenticator.
				authenticateUser(VALID_USERNAME, Password.createFromUncrypted(INVALID_PASSWORD));
		assertNull(user);
	}
}
