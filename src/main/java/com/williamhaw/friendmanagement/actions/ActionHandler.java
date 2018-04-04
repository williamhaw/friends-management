package com.williamhaw.friendmanagement.actions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.williamhaw.friendmanagement.persistence.UserPersistence;

/**
 * Routes JSON request to Actions
 * <p>
 * Separates JSON handling from action logic
 * @author williamhaw
 *
 */
public class ActionHandler {

	public enum Actions{
		ADD_USER,
		ADD_FRIENDS,
		GET_FRIENDS,
		COMMON_FRIENDS,
	}
	
	/**
	 * Action classes
	 */
	private AddFriendAction addFriend;
	private GetFriendsAction getFriends;
	private AddUserAction addUser;
	private GetCommonFriendsAction getCommonFriends;
	
	/*
	 * JSON keys
	 */
	public static final String KEY_FRIENDS = "friends";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_SUCCESS = "success";
	public static final String KEY_COUNT = "count";
	
	public ActionHandler(UserPersistence persistence) {		
		addFriend = new AddFriendAction(persistence);
		getFriends = new GetFriendsAction(persistence);
		addUser = new AddUserAction(persistence);
		getCommonFriends = new GetCommonFriendsAction(persistence);
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject handle(Actions action, JSONObject request) {
		
		JSONObject ret = new JSONObject();
		boolean success = false;
		
		
		switch (action) {
		case ADD_USER:
			if(request.get(KEY_EMAIL) != null) {
				String email = (String)request.get(KEY_EMAIL);
				Set<String> friends = new HashSet<>();
				if(request.get(KEY_FRIENDS) != null) {
					JSONArray friendsArray = (JSONArray)request.get(KEY_FRIENDS);
					for(Object friendEmail : friendsArray) {
						friends.add(friendEmail.toString());
					}
				}				
				success = addUser.addUser(email, friends);
			}
			break;
		case ADD_FRIENDS:
			if(request.get(KEY_FRIENDS) != null) {
				JSONArray friendsArray = (JSONArray)request.get(KEY_FRIENDS);
				HashSet<String> friends = new HashSet<String>();
				for(Object email : friendsArray) {
					friends.add(email.toString());
				}				
				success = addFriend.addFriends(friends);
			}else {
				success = false;
			}
			break;			
		case GET_FRIENDS:
			if(request.get(KEY_EMAIL) != null) {
				String email = (String)request.get(KEY_EMAIL);				
				Set<String> friends = getFriends.getFriends(email);
				if(friends != null) {
					ret.put(KEY_FRIENDS, friends);
					ret.put(KEY_COUNT, friends.size());
					success = true;
				}else {
					success = false;
				}
			}else {
				success = false;
			}
			break;
		case COMMON_FRIENDS:
			if(request.get(KEY_FRIENDS) != null) {
				JSONArray friendsArray = (JSONArray)request.get(KEY_FRIENDS);
				if(friendsArray.size() == 2) {
					ArrayList<String> friends = new ArrayList<>();
					for(Object email : friendsArray) {
						friends.add(email.toString());
					}
					Set<String> commonFriends = getCommonFriends.handle(friends.get(0), friends.get(1));
					ret.put(KEY_FRIENDS, commonFriends);
					ret.put(KEY_COUNT, commonFriends.size());
					success = true;					
				}else {
					success = false;
				}
			}
		default:
			break;
		}
		
		ret.put(KEY_SUCCESS, success);
				
		return ret;		
	}
	
	
}
