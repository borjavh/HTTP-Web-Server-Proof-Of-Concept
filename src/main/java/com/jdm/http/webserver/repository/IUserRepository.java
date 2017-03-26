package com.jdm.http.webserver.repository;

import com.jdm.http.webserver.model.User;

public interface IUserRepository{
	
	// get all the users as array.
	public User[] findAllUsers();
	
	// add a new user.
    public void addUser(User user);
    
    // find a user by its ID.
    public User findUserById(String id);
    
    // find a user by its username.
    public User findUserByUsername(String username);
}
