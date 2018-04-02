package com.williamhaw.friendmanagement.user;

import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a default User that only has an email and a Set of friends
 * <p>
 * User is defined by their unique email address.
 * @author williamhaw
 *
 */
public class DefaultUser implements User{
	private String email;
	private Set<String> friends = new HashSet<>();
	
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
	public void addFriends(Set<String> friends) {
		this.friends.addAll(friends);
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
}
