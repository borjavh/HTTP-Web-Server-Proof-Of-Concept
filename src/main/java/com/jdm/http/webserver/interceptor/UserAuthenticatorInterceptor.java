package com.jdm.http.webserver.interceptor;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import com.jdm.http.webserver.model.Password;
import com.jdm.http.webserver.model.User;
import com.jdm.http.webserver.repository.UserRepositoryImpl;
import com.jdm.http.webserver.utils.logger.Logger;

public class UserAuthenticatorInterceptor {
	
	private static Logger log = Logger.getLogger(UserAuthenticatorInterceptor.class);
	
    public UserAuthenticationResponse execute(UserAuthenticationRequest request) {
        try {
            UserAuthenticator userAuthenticator = new UserAuthenticator(new UserRepositoryImpl());
            
            // authenticate the incoming user.
            User user = userAuthenticator.
            		authenticateUser(request.getUsername(), Password.
            				createPasswordFromNoCypheredBytes(request.getPassword()));
            
            return new UserAuthenticationResponse(user);
        } catch (NoSuchAlgorithmException e) {
        	log.error(e.getMessage(), e);
        } catch (UnsupportedEncodingException e) {
        	log.error(e.getMessage(), e);
        }
        
        return new UserAuthenticationResponse(null);
    }
}
