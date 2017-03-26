package com.jdm.http.webserver.server;

import com.jdm.http.webserver.controller.BaseHandlerController;
import com.jdm.http.webserver.controller.LoginHandlerController;
import com.jdm.http.webserver.controller.LogoutHandlerController;
import com.jdm.http.webserver.controller.ContentHandlerController;
import com.jdm.http.webserver.utils.logger.Logger;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

@SuppressWarnings("restriction")
public class HttpWebServer {

	private static Logger log = Logger.getLogger(HttpWebServer.class);

	private static final int DEFAULT_PORT = 8080;
	private static int PORT = DEFAULT_PORT;
	
	public static void main(String[] args) throws IOException {
		getValidPortParam(args);

		HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT),0);
		httpServer.createContext("/", new BaseHandlerController());
		httpServer.createContext("/login", new LoginHandlerController());
		httpServer.createContext("/logout", new LogoutHandlerController());
		httpServer.createContext("/page", new ContentHandlerController());
		
		// Every time a job is submitted to the pool, if it should be started
		// running immediately then it is necessary to use "newCachedThreadPool()".
		httpServer.setExecutor(java.util.concurrent.Executors.newCachedThreadPool());
		httpServer.start();

		log.info(new StringBuilder("-> Server started at port ").append(PORT).toString());
	}

	private static void getValidPortParam(String[] args) {
		if (args.length > 0) {
			try {
				PORT = Integer.parseInt(args[0]);
			} catch (NumberFormatException exception) {
				log.info(new StringBuilder("-> Incorrect port number, using port ").append(PORT).toString());
			}
		}
	}

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		HttpWebServer.log = log;
	}
}
