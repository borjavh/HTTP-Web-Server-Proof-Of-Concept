package com.jdm.http.webserver.controller;

import java.io.IOException;

public class BaseHandlerController extends AbstractHandlerController {
    public void get() throws IOException {
        sendHttp200OK(render("/views/main.html"));
    }
}
