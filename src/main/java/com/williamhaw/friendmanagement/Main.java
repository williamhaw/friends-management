package com.williamhaw.friendmanagement;

import java.io.FileInputStream;
import java.util.Properties;

import com.williamhaw.friendmanagement.util.PropertiesHelper;

public class Main {

	
	
	public static void main(String[] args) {
		
		try {
			Properties p = new Properties();
			FileInputStream inputStream = new FileInputStream(args[0]);
			p.load(inputStream);
			inputStream.close();
			
			PropertiesHelper props = new PropertiesHelper(p);			
			
			FriendManagementServer server = new FriendManagementServer(props);
			server.start();
			
		} catch (Exception e) {
			System.err.println(e);
		}
		
	}

}
