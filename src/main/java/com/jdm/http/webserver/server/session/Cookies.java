package com.jdm.http.webserver.server.session;

import com.jdm.http.webserver.utils.date.DateUtil;
import com.sun.net.httpserver.Headers;

import java.net.HttpCookie;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("restriction")
public class Cookies implements Iterable<HttpCookie> {
	
    public static final String HEADER_NAME = "Cookie";
    private HashMap<String, HttpCookie> cookies = new HashMap<String, HttpCookie>();

    public Cookies(Headers headers) {
        String cookieHeader = headers.getFirst(HEADER_NAME);
        if (cookieHeader != null) {
            List<HttpCookie> cookiesList = HttpCookie.parse(cookieHeader);
            for (HttpCookie cookie : cookiesList) {cookies.put(cookie.getName(), cookie);}
        }
    }

    public ArrayList<String> getCookieHeader(){
        ArrayList<String> cookieHeaders = new ArrayList<String>();
        for (HttpCookie cookie:this) {cookieHeaders.add(transformCookieToString(cookie));}
        return cookieHeaders;
    }

    private String transformCookieToString(HttpCookie cookie) {
        StringBuilder sb = new StringBuilder();

        sb.append(cookie.getName()).append("=").append(cookie.getValue());
        if (cookie.getMaxAge() >= 0) {
            Date expirationDate = new Date();
            DateFormat cookieFormat = new SimpleDateFormat(DateUtil.PATTERN_RFC1123, Locale.ENGLISH);
            cookieFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            expirationDate.setTime(expirationDate.getTime() + (cookie.getMaxAge() * 1000));
            sb.append("; expires=").append(cookieFormat.format(expirationDate));
            sb.append("; Max-Age=").append(cookie.getMaxAge());
        }

        return sb.toString();
    }

    public HttpCookie get(String cookieName) {
        return cookies.get(cookieName);
    }

    public void set(HttpCookie httpCookie) {
        cookies.put(httpCookie.getName(), httpCookie);
    }

    public boolean isEmpty() {
        return cookies.isEmpty();
    }

    public Iterator<HttpCookie> iterator() {
        return cookies.values().iterator();
    }
}