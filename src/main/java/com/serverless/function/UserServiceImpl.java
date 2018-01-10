package com.serverless.function;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.serverless.dao.DynamoDBUserInfoDaoImpl;
import com.serverless.domain.UserInfo;
import com.serverless.util.Consts;

public class UserServiceImpl {
	private static final DynamoDBUserInfoDaoImpl userDAO = DynamoDBUserInfoDaoImpl.instance();
	 private static final Logger log = Logger.getLogger(UserServiceImpl.class);
	
	 private static volatile UserServiceImpl instance;


	    private UserServiceImpl() { }

	    public static UserServiceImpl instance() {

	        if (instance == null) {
	            synchronized(UserServiceImpl.class) {
	                if (instance == null)
	                    instance = new UserServiceImpl();
	            }
	        }
	        return instance;
	    }
	    
	    

	public List<UserInfo> getAllEventsHandler() {
		System.out.println("GetAllUsers invoked to scan table for ALL users");
		List<UserInfo> users = userDAO.findAllEvents();
		System.out.println("Found " + users.size() + " total events.");
		return users;
	}

	public UserInfo getUserByName(String name) throws UnsupportedEncodingException {

		if (null == name || name.isEmpty() || name.equals(Consts.UNDEFINED)) {
			System.out.println("GetEventsForTeam received null or empty team name");
			throw new IllegalArgumentException("Team name cannot be null or empty");
		}

		// String name = URLDecoder.decode(name.getTeamName(), "UTF-8");
		log.info("GetEventsForTeam invoked for team with name = " + name);
		Optional<UserInfo> user = userDAO.findUserByName(name);
		return user.get();
	}

	public void saveOrUpdateEvent(UserInfo user) {

		if (null == user) {
			System.out.println("SaveEvent received null input");
			throw new IllegalArgumentException("Cannot save null object");
		}

		System.out.println("Saving or updating user with name = " + user.getName() + " , date = " + user.getEmail());
		userDAO.saveOrUpdateUser(user);
		System.out.println("Successfully saved/updated event");
	}

	public void deleteEvent(UserInfo user) {

		if (null == user) {
			System.out.println("DeleteEvent received null input");
			throw new IllegalArgumentException("Cannot delete null object");
		}

		System.out.println("Deleting event for team = " + user.getName() + " , date = " + user.getEmail());
		userDAO.deleteUser(user.getName());
		System.out.println("Successfully deleted event");
	}
}
