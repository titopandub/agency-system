package controllers;

import models.*;
 
public class Security extends Secure.Security {
	
	static boolean authenticate(String username, String password) {
	    return User.connect(username, password) != null;
	}
    
    static boolean check(String profile) {
        User user = User.find("byUsername", connected()).first();
        if(user != null) {
        	if("isAdmin".equals(profile)) {
                return user.isAdmin;
            } else if("isManager".equals(profile)) {
            	return user.isManager;
            } else {
                return false;
            }
        } else {
        	return false;
        }
        
    }
    
    static void onDisconnected() {
        Application.index();
    }
    
    static void onAuthenticated() {
        Reports.upcoming();
    }
    
}
