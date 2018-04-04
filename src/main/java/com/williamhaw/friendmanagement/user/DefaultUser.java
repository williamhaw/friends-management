package com.williamhaw.friendmanagement.user;

import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a default User that contains an email, 
 * Set<String> of friend emails, Set<String> of blocked emails
 * <p>
 * User is defined by their unique email address.
 * @author williamhaw
 *
 */
public class DefaultUser implements User{
	private String email;
	private Set<String> friends = new HashSet<>();
	private Set<String> blocked = new HashSet<>();
	private Set<String> subscribers = new HashSet<>();
	
	public DefaultUser(String email) {
		this.email = email;
	}
	
	public DefaultUser(String email, Set<String> friends) {
		this.email = email;
		this.friends = friends;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public Set<String> getFriends() {
		return friends;
	}

	@Override
	public void addFriend(String friendEmail) {
		friends.add(friendEmail);		
	}
	
	@Override
	public void addFriends(Set<String> friends) {
		this.friends.addAll(friends);
	}
	
	@Override
	public Set<String> getBlocked() {
		return blocked;
	}

	@Override
	public void addBlocked(String toBlock) {
		this.blocked.add(toBlock);
	}
	
	@Override
	public void addBlocked(Set<String> toBlock) {
		this.blocked.addAll(toBlock);
	}

	@Override
	public Set<String> getSubscribers() {
		return subscribers;
	}

	@Override
	public void addSubscriber(String subscriber) {
		this.subscribers.add(subscriber);		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultUser other = (DefaultUser) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("DefaultUser [email=");
		sb.append(email);
		sb.append(", friends=[");
		for(String friend : friends) {
			sb.append(friend);
			sb.append(", ");
		}
		sb.append("]");
		sb.append(", blocked=[");
		for(String b : blocked) {
			sb.append(b);
			sb.append(", ");
		}
		sb.append("]");
		sb.append("]");
		
		return sb.toString();
	}
}
