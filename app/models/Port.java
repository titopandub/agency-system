package models;

import java.util.Vector;

import javax.persistence.Lob;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;

import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.modules.morphia.Model;

@Entity
public class Port extends Model {
	
	public String code;
	
	public String name;
	
	@Lob
    @MaxSize(1000)
	public String info;
	
	@Embedded
	public Tariff costtariff;
	
	public boolean isApproved;
	
//	@Embedded
//	public Tariff selltariff;
	
	public Port(String name) {
		this.name = name;
		this.isApproved = false;
//		this.selltariff = selltariff;
	}
	
	public Port(String name, Tariff costtariff) {
		this.name = name;
		this.costtariff = costtariff;
		this.isApproved = false;
//		this.selltariff = selltariff;
	}
	
	public Port(String code, String name, Tariff costtariff) {
		this.code = code;
		this.name = name;
		this.costtariff = costtariff;
		this.isApproved = false;
//		this.selltariff = selltariff;
	}
	
	public Integer[] getTugTariff() {
		int i = 0;
		Integer v[] = new Integer[this.costtariff.tug.size() + 1];
		while(i < this.costtariff.tug.size()) {
			v[i] = this.costtariff.tug.get(i).minimum;
			i++;
		}
		if(this.costtariff.tug.size() == 1) {
			v[1] = this.costtariff.tug.get(i-1).maximum;
		} else {
			v[i] = this.costtariff.tug.get(i-1).maximum;
		}
		return v;
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
