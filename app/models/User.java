package models;

import play.data.validation.Email;
import play.data.validation.Required;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

import play.modules.morphia.Model;

@Entity
public class User extends Model {
	
	public String username;
	
	public String password;
	
	public String email;
	
	public String fullname;
	
	public boolean isAdmin;
	
	public boolean isManager;
	
	public boolean isApproved;
	
	public User(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.isApproved = false;
	}
	
	public static User connect(String username, String password) {
		return find("byUsernameAndPassword", username, password).first();
	}
	
	public void approve(Boolean approve) {
		if(approve) {
			this.isApproved = true;
		} else {
			this.isApproved = false;
		}
	}
	
	public String toString() {
		return username;
	}

}
