package com.jdm.http.webserver.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class Password  implements Serializable {

	private static final long serialVersionUID = 705142778909038610L;

	private final static String ENCODING = "UTF-8";
    private boolean isCiphered;
    private byte[] password;
    
    private Password(byte[] password, boolean isCyphered) {
        this.password = password;
        this.isCiphered = isCyphered;
    }
    
    public boolean isCiphered()
    {
        return this.isCiphered;
    }
    
    public byte[] getPassword()
    {
        return this.password;
    }

    public static Password createPasswordFromCypheredBytes(byte[] password) {
        return new Password(password, true);
    }
    
    public static Password createPasswordFromNoCypheredBytes(byte[] password) {
        return new Password(password, false);
    }
    
    public static Password createFromUncrypted(String password) throws UnsupportedEncodingException {
        return createPasswordFromNoCypheredBytes(password.getBytes(ENCODING));
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Password pass2 = (Password) obj;
        if (this.isCiphered != pass2.isCiphered) {
            return false;
        }
        if (!Arrays.equals(this.password, pass2.password)) {
            return false;
        }
        return true;
    }
}