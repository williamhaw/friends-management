package com.williamhaw.friendmanagement.actions;

import java.util.Set;

import com.williamhaw.friendmanagement.persistence.PersistenceException;
import com.williamhaw.friendmanagement.persistence.UserPersistence;
import com.williamhaw.friendmanagement.user.DefaultUser;

/**
 * Adds User to persistence
 * @author williamhaw
 *
 */
public class AddUserAction {
	
	private UserPersistence persistence;
	
	public AddUserAction(UserPersistence persistence) {
		this.persistence = persistence;
	}
	
	/**
	 * @param email
	 * @param friends
	 * @return true if user is valid and persistence succeeds, false otherwise
	 */
	public boolean addUser(String email, Set<String> friends) {
		try {
			if(isValidUser(email, friends))
				return persistence.add(new DefaultUser(email, friends));
			else
				return false;
		} catch (PersistenceException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean isValidUser(String email, Set<String> friends) {
		if(email == null)
			return false;
		else if (friends == null) 
			return false;
		else 
			return true;
	}
}
