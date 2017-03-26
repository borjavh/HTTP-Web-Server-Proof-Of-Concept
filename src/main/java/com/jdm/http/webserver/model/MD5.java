package com.jdm.http.webserver.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	
    private static final String ALGORITHM = "MD5"; // basic encryption algorithm
    private final MessageDigest encrypter;
    
    public MD5() throws NoSuchAlgorithmException {
        this.encrypter = MessageDigest.getInstance(ALGORITHM);
    }
    
    public Password cypher(Password password) {
        return (!password.isCiphered()) ? 
            Password.createPasswordFromCypheredBytes(encrypter.digest(password.getPassword())) : password;
    }
}
