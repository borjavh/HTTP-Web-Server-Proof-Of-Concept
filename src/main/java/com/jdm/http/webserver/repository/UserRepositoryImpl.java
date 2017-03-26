package com.jdm.http.webserver.repository;

import java.util.ArrayList;

import com.jdm.http.webserver.model.MD5;
import com.jdm.http.webserver.model.Password;
import com.jdm.http.webserver.model.Permission;
import com.jdm.http.webserver.model.User;
import com.jdm.http.webserver.model.Role;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;

public class UserRepositoryImpl implements IUserRepository {

	private final ArrayList<User> users;

	public UserRepositoryImpl() throws UnsupportedEncodingException, NoSuchAlgorithmException {
		super();
		this.users = new ArrayList<User>();
		generateUsers();
	}
	
	// get all the users as array.
	public User[] findAllUsers() {
		return (User[]) this.users.toArray();
	}

	// add a new user.
	public void addUser(User user) {
		this.users.add(user);
	}

	// find a user by its ID.
	public User findUserById(String id) {
		for (User user : this.users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
	}

	// find a user by its username.
	public User findUserByUsername(String username) {
		for (User user : this.users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
	}

	// get all the users a List.
	public ArrayList<User> getUsers() {
		return users;
	}
	
	// add permissions
	private Role addPermissionsToRole(String rolename, String... permissions) {
        HashSet<Permission> rolePermissions = new HashSet<Permission>();
        for (String permission:permissions) {
            rolePermissions.add(new Permission(permission));
        }

        return new Role(rolename, rolePermissions);
    }
	
	// generate some users used for testing purposes.
	private void generateUsers() throws UnsupportedEncodingException, NoSuchAlgorithmException {
		Password defaultPassword = Password.createFromUncrypted("qwerty");
        defaultPassword = (new MD5()).cypher(defaultPassword);

        this.addUser(new User("user_1", defaultPassword, addPermissionsToRole("USER", "PAGE_1")));
        this.addUser(new User("user_2", defaultPassword, addPermissionsToRole("USER", "PAGE_2")));
        this.addUser(new User("user_3", defaultPassword, addPermissionsToRole("USER", "PAGE_3")));
        this.addUser(new User("user_4", defaultPassword, addPermissionsToRole("USER", "PAGE_1", "PAGE_3")));
        this.addUser(new User("user_5", defaultPassword, addPermissionsToRole("USER", "PAGE_3")));
        this.addUser(new User("admin", defaultPassword, addPermissionsToRole("ADMIN", "PAGE_1", "PAGE_2", "PAGE_3")));
	}
}