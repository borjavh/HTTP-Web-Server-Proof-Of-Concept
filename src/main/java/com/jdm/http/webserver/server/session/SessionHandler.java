package com.jdm.http.webserver.server.session;

import com.jdm.http.webserver.server.session.exception.SessionException;

abstract public class SessionHandler {
    protected Session session;

    abstract public void start() throws SessionException;

    abstract public void close();

    public Session getSession() {
        return session;
    }
}