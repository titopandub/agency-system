package models;

import java.util.Vector;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;

import play.data.validation.Required;
import play.modules.morphia.Model;

@Entity
public class Port extends Model {
	
	public String code;
	
	public String name;
	
	@Embedded
	public Tariff costtariff;
	
//	@Embedded
//	public Tariff selltariff;
	
	public Port(String code, String name) {
		this.code = code;
		this.name = name;
//		this.costtariff = new Tariff("", 0, 0, 0, 0, 0, 0);
//		this.selltariff = selltariff;
	}
	
	public Port(String code, String name, Tariff costtariff) {
		this.code = code;
		this.name = name;
		this.costtariff = costtariff;
//		this.selltariff = selltariff;
	}
	
	public Integer[] getTugTariff() {
		int i = 0;
		Integer v[] = new Integer[this.costtariff.tug.size() + 1];
		while(i < this.costtariff.tug.size()) {
			v[i] = this.costtariff.tug.get(i).minimum;
			i++;
		}
		
		v[i] = this.costtariff.tug.get(i-1).maximum;
		return v;
	}
	
	public String toString() {
		return name;
	}
	
}
