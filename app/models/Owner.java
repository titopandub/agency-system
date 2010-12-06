package models;

import javax.persistence.Lob;

import com.google.code.morphia.annotations.Entity;

import play.data.validation.Email;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.modules.morphia.Model;

@Entity
public class Owner extends Model {
	
	public String name;
	
	public String pic;
	
	public String pictelephone;
	
	@Lob
    @MaxSize(500)
	public String address;
	
	public String telephone;
	
	public String fax;
	
	public String email;
	
	public Owner(String name, String pic, String email) {
		this.name = name;
		this.pic = pic;
		this.email = email;
	}
	
	public String toString() {
		return name;
	}

}
