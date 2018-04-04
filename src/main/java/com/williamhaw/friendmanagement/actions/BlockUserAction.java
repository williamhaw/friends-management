package com.williamhaw.friendmanagement.actions;

import com.williamhaw.friendmanagement.persistence.PersistenceException;
import com.williamhaw.friendmanagement.persistence.UserPersistence;
import com.williamhaw.friendmanagement.user.User;

/**
 * Blocks 'requestor' user from seeing 'target' user's notifications
 * @author williamhaw
 *
 */
public class BlockUserAction {

	private UserPersistence persistence;
	
	public BlockUserAction(UserPersistence persistence) {
		this.persistence = persistence;
	}
	
	public boolean handle(String requestor, String target) {
		try {
			User user = persistence.lookup(requestor);
			if(user == null) {
				return false;
			}
			user.addBlocked(target);
			persistence.update(user);
			return true;
		} catch (PersistenceException e) {
			e.printStackTrace();
			return false;
		}
	}
}
