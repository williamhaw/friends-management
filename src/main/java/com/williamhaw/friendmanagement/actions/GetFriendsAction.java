package com.williamhaw.friendmanagement.actions;

import java.util.Set;

import com.williamhaw.friendmanagement.persistence.PersistenceException;
import com.williamhaw.friendmanagement.persistence.UserPersistence;
import com.williamhaw.friendmanagement.user.User;

/**
 * Retrieves friends list for User
 * @author williamhaw
 *
 */
public class GetFriendsAction {
	
	private UserPersistence persistence;
	
	public GetFriendsAction(UserPersistence persistence) {
		this.persistence = persistence;
	}

	/**
	 * @param email
	 * @return null on PersistenceException, else returns Set<String> of friend emails
	 */
	public Set<String> getFriends(String email) {
		
		try {
			User user = persistence.lookup(email);
			return user.getFriends();
		}catch (PersistenceException e) {
			e.printStackTrace();
			return null;
		}
	}

}
