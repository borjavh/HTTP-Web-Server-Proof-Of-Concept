package com.jdm.http.webserver.model;

import junit.framework.TestCase;
import com.jdm.http.webserver.model.MD5;
import com.jdm.http.webserver.model.Password;


import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class MD5Test extends TestCase{

	public static final String PASSWORD = "qwerty";
    public static final byte[] CRYPTED_PASSWORD_BYTES = new byte[]{-40, 87, -114, -33, -124, 88, -50, 6, -5, -59, -69, 118, -91, -116, 92, -92};
	
    private MD5 md5;

    @Override
    protected void setUp() throws Exception {
        md5 = new MD5();
    }

    public void testCypherPassword() throws UnsupportedEncodingException {
        Password cypheredPass =  md5.cypher(Password.createFromUncrypted(PASSWORD));
        assertTrue(cypheredPass.isCiphered());
        assertTrue(Arrays.equals(CRYPTED_PASSWORD_BYTES, cypheredPass.getPassword()));
    }

    public void testSameCypheredPassword() {
        Password cypheredPass = Password.createPasswordFromCypheredBytes(CRYPTED_PASSWORD_BYTES);
        Password cypheredPassword = md5.cypher(cypheredPass);
        assertSame(cypheredPassword, cypheredPass);
    }
}
