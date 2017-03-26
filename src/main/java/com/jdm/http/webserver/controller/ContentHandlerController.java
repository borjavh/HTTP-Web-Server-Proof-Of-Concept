package com.jdm.http.webserver.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;

public class ContentHandlerController extends AbstractHandlerController {
    public void get() throws IOException {
        HashMap<String, String> parameters = getUriParameters();
        HashMap<String, String> modelProperties = new HashMap<String, String>();
        String pageName = parameters.get("pageID");
        if (pageName == null) {
            sendHttp404ERROR();
        } else if (getSessionData().hasPermission(pageName)) {
            modelProperties.put("PAGE_NAME", pageName);
            sendHttp200OK(render("/views/content.html", modelProperties));
        } else {
            modelProperties.put("LOGIN_URL", "/login?error=" + URLEncoder.encode("Incorrect permissions. You are not allow to see this content", "UTF-8"));
            sendHttp403ERROR(render("/views/notauthorized.html", modelProperties));
        }
    }
}
