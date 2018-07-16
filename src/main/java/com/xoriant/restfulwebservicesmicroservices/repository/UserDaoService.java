package com.xoriant.restfulwebservicesmicroservices.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.xoriant.restfulwebservicesmicroservices.model.User;

@Component
public class UserDaoService {
	
	static List<User> userList = new ArrayList<>();
	private int userIdCounter = 3; 
	
	static {
		userList.add(new User(1,"Adam",new Date()));
		userList.add(new User(2,"Eve",new Date()));
		userList.add(new User(3,"Jack",new Date()));
	}
	
	public List<User> findAll(){
		return userList;
	}
	
	public User save(User user) {
		if(user.getUserId() == null) {
			user.setUserId(++userIdCounter);
		}
		userList.add(user);
		return user;
	}
	
	public User findOne(int id) {
		for(User user : userList) {
			if(user.getUserId() == id)
				return user;
		 }
		return null;
	}
	
	public User deleteUser(int id) {
		Iterator<User> itr = userList.iterator();
		while(itr.hasNext()) {
			User user = itr.next();
			if(user.getUserId() == id) {
				userList.remove(user);
				return user;
			}
		}
		return null;
	}

}
