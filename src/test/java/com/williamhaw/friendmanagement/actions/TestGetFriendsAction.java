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

/**
 * @author williamhaw
 *
 */
public class TestGetFriendsAction {

	GetFriendsAction action;
	HashMapUserPersistence persistence;
	
	Set<String> friends;
	
	@Before
	public void setUp() throws Exception {
		
		persistence = new HashMapUserPersistence();
		
		friends = new HashSet<String>(Arrays.asList("friend2@williamhaw.com", "friend3@williamhaw.com", "friend4@williamhaw.com"));	
		
		User friend1 = new DefaultUser("friend1@williamhaw.com", friends);
		
		persistence.add(friend1);
		
		action = new GetFriendsAction(persistence);	
	}

	/**
	 * Behaviour is only correct iff size of friends set is equal to size of getFriends set and all members
	 * of the friend set are in the getFriend set
	 */
	@Test
	public void successCase() {
		
		Set<String> getFriends = action.getFriends("friend1@williamhaw.com");
		
		assertTrue(friends.size() == getFriends.size());
		
		for(String friend : friends)
			assertTrue(getFriends.contains(friend));
		
	}
	
	@Test
	public void failCase() {		
		Set<String> getFriends = action.getFriends("friend1@williamhaw.com");		
		assertFalse(getFriends.contains("friend5@williamhaw.com"));		
	}

}
