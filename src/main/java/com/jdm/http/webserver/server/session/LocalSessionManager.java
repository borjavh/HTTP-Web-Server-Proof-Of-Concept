package com.jdm.http.webserver.server.session;

import java.io.*;
import java.net.HttpCookie;
import java.net.URISyntaxException;

import com.jdm.http.webserver.server.session.exception.SessionException;

public class LocalSessionManager extends SessionHandler {
	
    public static final int EXPIRATION = 5 * 60; // five minutes.
    private String localSessionSaveDirectory;
    private Cookies cookies;
    
    public LocalSessionManager(Cookies cookies) throws SessionException, URISyntaxException {
        this.cookies = cookies;

        String execPath = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
        localSessionSaveDirectory = new StringBuilder(execPath).append("/sessions").toString();

        File sessionFolder = new File(localSessionSaveDirectory);
        if (!sessionFolder.exists()) {
            if (!sessionFolder.mkdir()) {
                throw new SessionException("An error ocurred while creating sessions folder");
            }
        }
    }

    public void start() throws SessionException {
        if (this.session == null) {
            Session cookieSession = null;
            try {
                cookieSession = getSessionFromCookies();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            if (cookieSession != null) {
                this.session = cookieSession;
            } else {
                Session sessionStorage = new Session();
                HttpCookie sessionId = new HttpCookie("SESSION_ID", sessionStorage.getId());
                sessionId.setMaxAge(EXPIRATION);
                cookies.set(sessionId);

                this.session = sessionStorage;
            }
        }
    }

    private String getSessionStoragePath(String sessionId) {
        return localSessionSaveDirectory + "/" + sessionId;
    }
    
    @SuppressWarnings("resource")
	public void close() {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                    new FileOutputStream(getSessionStoragePath(session.getId()))
            );
            objectOutputStream.writeObject(session);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("resource")
	private Session getSessionFromCookies() throws ClassNotFoundException {
        HttpCookie sessionId = cookies.get("SESSION_ID");

        if (sessionId != null) {
            sessionId.setMaxAge(EXPIRATION); //Renovates expiration date
            String sessionIdValue = sessionId.getValue();
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(
                        new FileInputStream(getSessionStoragePath(sessionIdValue))
                );

                return (Session) objectInputStream.readObject();
            } catch (IOException e) {
                // In case session file is not found, will create a new one
                System.err.println("Session file (" + sessionIdValue + ") not found");
                e.printStackTrace();
            }
        }

        return null;
    }
}
