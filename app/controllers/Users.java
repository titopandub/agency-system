package controllers;

import models.User;
import play.*;
import play.i18n.Messages;
import play.mvc.*;

import play.*;
import play.mvc.*;

@With(Secure.class)
public class Users extends CRUD {
	
	public static void changePass(Long id, String oldPassword, 
			String newPassword) {
		User user = User.findById(id);
		boolean isSuccess;
		
		isSuccess = user.changePass(oldPassword, newPassword);
		if(isSuccess == true) {
			flash.success(Messages.get(user.username, "password changed"));
			redirect(request.controller);
		} else {
			flash.success(Messages.get(user.username, "please retry again"));
			redirect(request.controller);
		}
	}

}
