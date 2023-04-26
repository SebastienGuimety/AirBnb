package com.example.airbnb.models;

public class Session {

    private static Session instance;
    private User currentUser;

    private Session() {}

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void login(User user) {
        currentUser = user;
    }

    public void logout() {
        currentUser = null;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public User getCurrentUser() {
        return currentUser;
    }
    public boolean isHote(User user) {
    	if(user.role.equals("hote")) {
    		return true;
    	}
    	return false;
    }
}



