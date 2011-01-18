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
	
	public User(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public static User connect(String username, String password) {
		return find("byUsernameAndPassword", username, password).first();
	}
	
	public String toString() {
		return username;
	}

}
