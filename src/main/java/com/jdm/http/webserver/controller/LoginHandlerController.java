package com.jdm.http.webserver.controller;

import java.io.IOException;
import java.util.HashMap;

import com.jdm.http.webserver.interceptor.UserAuthenticationRequest;
import com.jdm.http.webserver.interceptor.UserAuthenticationResponse;
import com.jdm.http.webserver.interceptor.UserAuthenticatorInterceptor;

public class LoginHandlerController extends AbstractHandlerController {
	
    public void get() throws IOException {
    	
        HashMap<String, String> parameters = getUriParameters();
        HashMap<String, String> modelProperties = new HashMap<String, String>();
        if (parameters.get("error") != null) {
            modelProperties.put("ERROR", parameters.get("error"));
        }

        sendHttp200OK(render("/views/login.html", modelProperties));
    }

    public void post() throws IOException {
        HashMap<String, String> bodyParams = getRequestBodyParameters();

        // use the user authenticator interceptor to get the user credentials
        UserAuthenticationResponse authenticateUserResponse = new UserAuthenticatorInterceptor().
        		execute(new UserAuthenticationRequest(bodyParams.get("username"), bodyParams.get("password")));

        getSessionData().setUser(authenticateUserResponse.getUser());

        if (authenticateUserResponse.isAuthenticated()) {
            redirectTemporarly("/");
        } else {
            redirectTemporarly("/login?error=Invalid%20Credentials");
        }
    }
}
