package com.williamhaw.friendmanagement.actions;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.williamhaw.friendmanagement.persistence.HashMapUserPersistence;
import com.williamhaw.friendmanagement.user.DefaultUser;
import com.williamhaw.friendmanagement.user.User;

public class TestGetCommonFriendsAction {

	GetCommonFriendsAction action;
	HashMapUserPersistence persistence;
	
	@Before
	public void setUp() throws Exception {
		
		persistence = new HashMapUserPersistence();
		
		User friend1 = new DefaultUser("friend1@williamhaw.com", new HashSet<>(Arrays.asList("friend3@williamhaw.com", "friend4@williamhaw.com", "friend5@williamhaw.com")));
		persistence.add(friend1);
		
		User friend2 = new DefaultUser("friend2@williamhaw.com", new HashSet<>(Arrays.asList("friend3@williamhaw.com", "friend5@williamhaw.com")));
		persistence.add(friend2);
		
		action = new GetCommonFriendsAction(persistence);		
	}

	@Test
	public void successCase() {
		Set<String> commonFriends = action.handle("friend1@williamhaw.com", "friend2@williamhaw.com");
		assertTrue(commonFriends.containsAll(new HashSet<>(Arrays.asList("friend3@williamhaw.com", "friend5@williamhaw.com"))));
	}

}
