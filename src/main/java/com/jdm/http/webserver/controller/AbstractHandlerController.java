package com.jdm.http.webserver.controller;

import com.jdm.http.webserver.server.session.Cookies;
import com.jdm.http.webserver.server.session.LocalSessionManager;
import com.jdm.http.webserver.server.session.Session;
import com.jdm.http.webserver.server.session.SessionHandler;
import com.jdm.http.webserver.server.session.exception.SessionException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

@SuppressWarnings("restriction")
abstract public class AbstractHandlerController implements HttpHandler {
	
    private HttpExchange httpExchange;
    private Cookies cookies;
    private SessionHandler sessionHandler;
    private HashMap<String, String> modelProperties;

    // every http exchange is processed by this method.
    public void handle(HttpExchange httpExchange) throws IOException {
        this.httpExchange = httpExchange;
        this.cookies = new Cookies(httpExchange.getRequestHeaders());

        try {
            this.sessionHandler = new LocalSessionManager(cookies);
            this.sessionHandler.start();
        } catch (SessionException e){
        	e.printStackTrace();
            return;
        }catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        initializeModelProperties();

        String requestMethod = httpExchange.getRequestMethod().toLowerCase();
        try {
            Method method = this.getClass().getDeclaredMethod(requestMethod);
            method.invoke(this);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            sendHttp404ERROR();
        } catch (InvocationTargetException e) {
        	e.printStackTrace();
            sendHttp404ERROR();
        } catch (IllegalAccessException e) {
        	e.printStackTrace();
            sendHttp404ERROR();
        }
    }

    private void initializeModelProperties() {
        this.modelProperties = new HashMap<String, String>();
        if (getSessionData().hasUser()) {
            modelProperties.put("USERNAME", getSessionData().getUser().getUsername());
        }
    }

    protected HashMap<String, String> getUriParameters() {
        String queryString = httpExchange.getRequestURI().getQuery();
        if (queryString == null) {
            queryString = "";
        }

        return parseQueryString(queryString);
    }

    @SuppressWarnings("resource")
	protected HashMap<String, String> getRequestBodyParameters() {
        InputStream requestBody = getHttpExchange().getRequestBody();
        Scanner scanner = new Scanner(requestBody).useDelimiter("\\A");
        String queryString = scanner.next();
        return parseQueryString(queryString);
    }

    // return the query string params contained in the request.
    private HashMap<String, String> parseQueryString(String queryString) {
        HashMap<String, String> parameters = new HashMap<String, String>();

        String[] queryParams = queryString.split("&");
        for (String queryParam : queryParams) {
            int firstEqualPosition = queryParam.indexOf('=');
            if (firstEqualPosition == -1) {
                parameters.put(queryParam, null);
            } else {
                String queryParamName = queryParam.substring(0, firstEqualPosition);
                String queryParamValue = null;
                try {
                    queryParamValue = URLDecoder.decode(queryParam.substring(firstEqualPosition + 1), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                parameters.put(queryParamName, queryParamValue);
            }
        }

        return parameters;
    }

    // Http response.
    private void sendHttpResponse(int httpStatusCode, String responseBody) throws IOException {
        sessionHandler.close();
        if (!cookies.isEmpty()) {
            for (String cookieHeaderValue : cookies.getCookieHeader()) {
                httpExchange.getResponseHeaders().add("Set-Cookie", cookieHeaderValue);
            }
        }
        httpExchange.sendResponseHeaders(httpStatusCode, responseBody.getBytes().length);
        httpExchange.getResponseBody().write(responseBody.getBytes());
        httpExchange.close();
    }

    protected String render(String viewPath) {
        return render(viewPath, modelProperties);
    }

    @SuppressWarnings("resource")
	protected String render(String viewPath, HashMap<String, String> vars) {
        modelProperties.putAll(vars);

        InputStream resource = getClass().getResourceAsStream(viewPath);
        Scanner scanner = new Scanner(resource).useDelimiter("\\A");

        String modelContent = scanner.next();
        for (Map.Entry<String, String> var : modelProperties.entrySet()) {
            modelContent = modelContent.replaceAll(
                    Pattern.quote("{{ " + var.getKey() + " }}"),
                    var.getValue()
            );
        }
        
        modelContent = modelContent.replaceAll("\\{\\{ \\w+ \\}\\}", "");

        return modelContent;
    }

    // Http 200 ok.
    protected void sendHttp200OK(String responseBody) throws IOException {
        sendHttpResponse(200, responseBody);
    }

    // Http 304 Error.
    protected void sendHttp403ERROR(String responseBody) throws IOException {
        sendHttpResponse(403, responseBody);
    }
    
    // Http 404 Error.
    protected void sendHttp404ERROR() throws IOException {
        sendHttpResponse(404, "404 Not Found");
    }

    // Http 503 error.
    protected void sendHttp503ERROR() throws IOException {
        sendHttpResponse(503, "503 Server Error");
    }

    // Http 302 redirect.
    protected void redirectTemporarly(String path) throws IOException {
        httpExchange.getResponseHeaders().add("Location", path);
        sendHttpResponse(302, "");
    }
    
    protected HttpExchange getHttpExchange() {
        return httpExchange;
    }

    protected Cookies getCookies() {
        return cookies;
    }

    protected Session getSessionData() {
        return sessionHandler.getSession();
    }
}
