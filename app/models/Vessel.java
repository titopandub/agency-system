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
	
	public boolean isApproved;
	
	public Vessel(String name, int grt, Owner owner) {
		this.name = name;
		this.grt = grt;
		this.owner = owner;
		this.isApproved = false;
	}
	
	public Vessel(String name, int grt) {
		this.name = name;
		this.grt = grt;
		this.isApproved = false;
	}
	
	public Vessel(String code, String name, int grt, Owner owner) {
		this.code = code;
		this.name = name;
		this.grt = grt;
		this.owner = owner;
		this.isApproved = false;
	}
	
	public void approve(Boolean approve) {
		if(approve) {
			this.isApproved = true;
		} else {
			this.isApproved = false;
		}
	}
	
	public String toString() {
		return name;
	}

}
