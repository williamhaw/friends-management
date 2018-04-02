package com.williamhaw.friendmanagement;

import org.eclipse.jetty.server.Server;

public class Main {

	public static void main(String[] args) {
		Server jettyServer = new Server(8080);
		try {
			jettyServer.start();
			jettyServer.join();
		} catch (Exception e) {
			System.err.println(e);
		}
		
	}

}
