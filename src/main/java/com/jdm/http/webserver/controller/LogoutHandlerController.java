package com.jdm.http.webserver.controller;

import java.io.IOException;

public class LogoutHandlerController extends AbstractHandlerController {
    public void get() throws IOException {
        getSessionData().logoutUser();
        redirectTemporarly("/");
    }
}
