package com.williamhaw.friendmanagement.actions;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.williamhaw.friendmanagement.persistence.HashMapUserPersistence;
import com.williamhaw.friendmanagement.persistence.PersistenceException;
import com.williamhaw.friendmanagement.user.DefaultUser;
import com.williamhaw.friendmanagement.user.User;

/**
 * @author williamhaw
 *
 */
public class TestAddFriendAction {

	AddFriendAction action;
	HashMapUserPersistence persistence;
	
	@Before
	public void setUp() throws Exception {
		
		persistence = new HashMapUserPersistence();
		
		User friend1 = new DefaultUser("friend1@williamhaw.com");
		persistence.add(friend1);
		
		User friend2 = new DefaultUser("friend2@williamhaw.com");
		persistence.add(friend2);
		
		action = new AddFriendAction(persistence);		
	}

	@Test
	public void successCase() {
		
		try {
			action.addFriends(Arrays.asList("friend1@williamhaw.com", "friend2@williamhaw.com"));
			
			User user1 = persistence.lookup("friend1@williamhaw.com");
			assertTrue(user1.getFriends().contains("friend2@williamhaw.com"));
			assertTrue(user1.getFriends().size() == 1);
			
			User user2 = persistence.lookup("friend2@williamhaw.com");
			assertTrue(user2.getFriends().contains("friend1@williamhaw.com"));
			assertTrue(user2.getFriends().size() == 1);
			
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
	}

}
