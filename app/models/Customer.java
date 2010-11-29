package models;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;

import play.data.validation.Email;
import play.data.validation.Required;
import play.modules.morphia.Model;

@Entity
public class Customer extends Model {
	
	public String name;
	
	public String pic;
	
	public String address;
	
	public String telephone;
	
	public String fax;
	
	public String email;
	
	public Customer(String name, String address, String email) {
		this.name = name;
		this.address = address;
		this.email = email;
	}
	
	public String toString() {
		return name;
	}

}
