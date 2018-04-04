package com.williamhaw.friendmanagement.actions;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.williamhaw.friendmanagement.persistence.HashMapUserPersistence;
import com.williamhaw.friendmanagement.user.DefaultUser;
import com.williamhaw.friendmanagement.user.User;

public class TestGetRecipientsAction {

	GetRecipientsAction getRecipients;
	HashMapUserPersistence persistence;
	
	User subscriber = new DefaultUser("friend1@williamhaw.com");
	User blockedUpdates = new DefaultUser("friend2@williamhaw.com");
	User sender = new DefaultUser("friend3@williamhaw.com");
	User senderFriend = new DefaultUser("friend4@williamhaw.com");
	User mentioned = new DefaultUser("friend5@williamhaw.com");
	User mentionedBlocked = new DefaultUser("friend6@williamhaw.com");
	
	@Before
	public void setUp() throws Exception {
		
		persistence = new HashMapUserPersistence();
		
		sender.addFriend(senderFriend.getEmail());
		
		sender.addSubscriber(subscriber.getEmail());
		sender.addSubscriber(blockedUpdates.getEmail());
		
		blockedUpdates.addBlocked(sender.getEmail());
		mentionedBlocked.addBlocked(sender.getEmail());
		
		persistence.add(subscriber);
		persistence.add(blockedUpdates);
		persistence.add(sender);
		persistence.add(senderFriend);
		persistence.add(mentioned);
		persistence.add(mentionedBlocked);
		
		getRecipients = new GetRecipientsAction(persistence);
	}

	@Test
	public void test() {
		Set<String> recipients = getRecipients.handle(sender.getEmail(), "Welcome friend5@williamhaw.com and friend6@williamhaw.com");
		
		assertTrue(recipients.contains(senderFriend.getEmail()));
		assertTrue(recipients.contains(subscriber.getEmail()));
		assertTrue(recipients.contains(mentioned.getEmail()));
		
		assertFalse(recipients.contains(blockedUpdates.getEmail()));
		assertFalse(recipients.contains(mentionedBlocked.getEmail()));
	}

}
