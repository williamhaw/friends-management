package com.williamhaw.friendmanagement.actions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.williamhaw.friendmanagement.persistence.HashMapUserPersistence;
import com.williamhaw.friendmanagement.user.DefaultUser;
import com.williamhaw.friendmanagement.user.User;

public class TestSubscribeUpdatesAction {

	SubscribeUpdatesAction action;
	HashMapUserPersistence persistence;
	
	User friend1 = new DefaultUser("friend1@williamhaw.com");
	User friend2 = new DefaultUser("friend2@williamhaw.com");
	User friend3 = new DefaultUser("friend3@williamhaw.com");
	
	@Before
	public void setUp() throws Exception {
		
		persistence = new HashMapUserPersistence();
		
		friend2.addBlocked(friend3.getEmail());
		
		persistence.add(friend1);
		persistence.add(friend2);
		persistence.add(friend3);
		
		action = new SubscribeUpdatesAction(persistence);	
	}

	@Test
	public void successCase() {
		action.handle(friend1.getEmail(), friend2.getEmail());
		assertTrue(friend2.getSubscribers().contains(friend1.getEmail())); //check friend1 is subscribed to friend2
		
		action.handle(friend2.getEmail(), friend3.getEmail());
		assertFalse(friend3.getSubscribers().contains(friend2.getEmail())); //check friend2 is not subscribed to friend3 as friend2 has blocked friend3
	}

}
