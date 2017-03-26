package com.jdm.http.webserver.interceptor;


import java.security.NoSuchAlgorithmException;

import com.jdm.http.webserver.model.MD5;
import com.jdm.http.webserver.model.Password;
import com.jdm.http.webserver.model.User;
import com.jdm.http.webserver.repository.IUserRepository;

public class UserAuthenticator {
	
    private final IUserRepository userRepository;
    private final MD5 passwordEncrypter;

    public UserAuthenticator(IUserRepository userRepository) throws NoSuchAlgorithmException {
        this.userRepository = userRepository;
        this.passwordEncrypter = new MD5();
    }
    
    public User authenticateUser(String username, Password password){
        User user = this.userRepository.findUserByUsername(username);
        if (user != null) {
            Password cypheredpassword = passwordEncrypter.cypher(password);
            if (cypheredpassword.equals(user.getPassword())) {
                return user;
            }
        }
        return null;
    }
}
