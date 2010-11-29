package models;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Reference;

import play.data.validation.Required;
import play.modules.morphia.Model;

@Entity
public class Vessel extends Model {
	
	public String code;
	
	public String name;
	
	public int grt;
	
	@Reference
	public Owner owner;
	
	public Vessel(String code, String name, int grt, Owner owner) {
		this.code = code;
		this.name = name;
		this.grt = grt;
		this.owner = owner;
	}
	
	public String toString() {
		return name;
	}

}
