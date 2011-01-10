package models;

import javax.persistence.Lob;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Reference;

import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.modules.morphia.Model;

@Entity
public class Vessel extends Model {
	
	public String code;
	
	public String name;
	
	public String flag;
	
	public String built;
	
	public int dwt;
	
	public int grt;
	
	public int nrt;
	
	public Double loa;
	
	public Double beam;
	
	public int ha;
	
	public int ho;
	
	public String derricks;
	
	@Reference
	public Owner owner;
	
	@Lob
    @MaxSize(500)
	public String note;
	
	public Vessel(String name, int grt) {
		this.name = name;
		this.grt = grt;
	}
	
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
