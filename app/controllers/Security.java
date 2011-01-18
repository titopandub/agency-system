package controllers;

import models.*;
 
public class Security extends Secure.Security {
    
	static boolean authenticate(String username, String password) {
	    return User.connect(username, password) != null;
	}
    
    static boolean check(String profile) {
        User user = User.find("byEmail", connected()).first();
        if ("isAdmin".equals(profile)) {
            return user.isAdmin;
        } else if ("isManager".equals(profile)) {
        	return user.isManager;
        } else {
            return false;
        }
    }
    
}
