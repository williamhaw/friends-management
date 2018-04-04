package com.williamhaw.friendmanagement.actions;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import com.williamhaw.friendmanagement.persistence.HashMapUserPersistence;
import com.williamhaw.friendmanagement.persistence.PersistenceException;
import com.williamhaw.friendmanagement.user.DefaultUser;
import com.williamhaw.friendmanagement.user.User;

public class TestAddUser {
	
	AddUserAction action;
	HashMapUserPersistence persistence;
	User friend1;

	@Before
	public void setUp() throws Exception {
		persistence = new HashMapUserPersistence();
		friend1 = new DefaultUser("friend1@williamhaw.com", new HashSet<>(Arrays.asList("friend2@williamhaw.com","friend3@williamhaw.com")));
		persistence.add(friend1);
		
		action = new AddUserAction(persistence);
	}

	@Test
	public void successCase() {
		try {
			User persistedUser = persistence.lookup("friend1@williamhaw.com");
			assertTrue(friend1.getEmail().equals(persistedUser.getEmail()));
			assertTrue(friend1.getFriends().containsAll(persistedUser.getFriends()));
			
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
		
	}

}
