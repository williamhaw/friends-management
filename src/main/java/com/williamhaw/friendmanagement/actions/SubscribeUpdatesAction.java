package com.williamhaw.friendmanagement.actions;

import com.williamhaw.friendmanagement.persistence.PersistenceException;
import com.williamhaw.friendmanagement.persistence.UserPersistence;
import com.williamhaw.friendmanagement.user.User;

/**
 * Subscribes one user to notifications from another user if that user is not already blocked
 * @author williamhaw
 *
 */
public class SubscribeUpdatesAction {
	
	private UserPersistence persistence;

	public SubscribeUpdatesAction(UserPersistence persistence) {
		this.persistence = persistence;
	}
	
	/**
	 * @param requestor email address
	 * @param target email address
	 * @return true if subscribe action succeeded
	 */
	public boolean handle(String requestor, String target) {
		try {
			User targetUser = persistence.lookup(target);
			User requestorUser = persistence.lookup(requestor);
			
			if(requestorUser.getBlocked().contains(target))
				return false;
			
			targetUser.addSubscriber(requestor);
			persistence.update(targetUser);
			return true;
		} catch (PersistenceException e) {
			e.printStackTrace();
			return false;
		}
	}
}
