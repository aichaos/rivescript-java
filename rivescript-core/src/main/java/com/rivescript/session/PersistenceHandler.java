package com.rivescript.session;

import java.util.Map;

/**
 * Provides an interface for persisting the userdata.
 *
 * @author Balachandar S
 */
public interface PersistenceHandler {
	
	public void createUserData(String username, UserData userdata);
	
	public void updateUserData(String username, UserData userdata);
	
	public UserData getUserData(String username);
	
	public void deleteUserData(String username);
	
	public Map<String,UserData> getAllUserData();
	
	public void deleteAllUserData();
	
}
