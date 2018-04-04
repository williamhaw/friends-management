package com.williamhaw.friendmanagement.persistence;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import com.williamhaw.friendmanagement.user.DefaultUser;
import com.williamhaw.friendmanagement.user.User;
import com.williamhaw.friendmanagement.util.PropertiesHelper;

public class TestMongoDBUserPersistence {

	MongoDBUserPersistence persistence;
	
	@Before
	public void setUp() throws Exception {
		
		Properties p = new Properties();
		p.setProperty("db.uri", "mongodb+srv://friend_management_app:HuUNoEmBX3UbDOSM@cluster0-wrzlb.mongodb.net/");
		p.setProperty("db.name", "friend-management-app");
		p.setProperty("db.collection.name", "test-users");
		
		PropertiesHelper props = new PropertiesHelper(p);
		
		persistence = new MongoDBUserPersistence();
		persistence.initialise(props);
	}

	@Test
	public void test() {
		User friend1 = new DefaultUser("friend1@williamhaw.com", new HashSet<>(Arrays.asList("friend2@williamhaw.com")));
		
		try {
			persistence.add(friend1);
			User addTest = persistence.lookup(friend1.getEmail());
			
			assertTrue(addTest.getEmail().equals(friend1.getEmail()));
			assertTrue(addTest.getFriends().containsAll(friend1.getFriends()));
			
			friend1.addBlocked("friend3@williamhaw.com");
			persistence.update(friend1);
			
			User updateTest = persistence.lookup(friend1.getEmail());
			assertTrue(updateTest.getEmail().equals(friend1.getEmail()));
			assertTrue(updateTest.getFriends().containsAll(friend1.getFriends()));
			assertTrue(updateTest.getBlocked().containsAll(friend1.getBlocked()));
			
			persistence.remove(friend1.getEmail());
			assertNull(persistence.lookup(friend1.getEmail()));
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
	}

}
