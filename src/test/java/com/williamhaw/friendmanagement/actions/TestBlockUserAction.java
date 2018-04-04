package com.williamhaw.friendmanagement.actions;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.williamhaw.friendmanagement.persistence.HashMapUserPersistence;
import com.williamhaw.friendmanagement.user.DefaultUser;
import com.williamhaw.friendmanagement.user.User;

public class TestBlockUserAction {

	BlockUserAction blockUser;
	AddFriendAction addFriend;
	HashMapUserPersistence persistence;
	User friend1 = new DefaultUser("friend1@williamhaw.com");
	User friend2 = new DefaultUser("friend2@williamhaw.com");

	@Before
	public void setUp() throws Exception {
		persistence = new HashMapUserPersistence();		
		persistence.add(friend1);
		persistence.add(friend2);
		blockUser = new BlockUserAction(persistence);
		addFriend = new AddFriendAction(persistence);
	}

	@Test
	public void test() {
		blockUser.handle(friend2.getEmail(), friend1.getEmail());
		assertTrue(friend2.getBlocked().contains(friend1.getEmail())); //check friend2 is blocked from seeing friend1
		assertFalse(friend1.getBlocked().contains(friend2.getEmail())); //friend1 still sees notifications from friend2
		
		addFriend.addFriends(Arrays.asList(friend1.getEmail(), friend2.getEmail()));
		//check friend2 and friend1 are not friends
		assertFalse(friend2.getFriends().contains(friend1.getEmail()));
		assertFalse(friend1.getFriends().contains(friend2.getEmail()));
	}

}
