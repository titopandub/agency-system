package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import play.modules.morphia.Model;

import com.google.code.morphia.annotations.Embedded;

@Embedded
public class Departure extends Model {
	
	public Date atd;
	
	public Double departTugOut;
	
	public String cargo;
	
	public int cargoWeight;
	
	@Embedded
	public List<Additional> additional;
	
	//Calculation
	public Double harbor;
	public Double queue;
	public Double pilot;
	public Double light;
	public Double tugfix;
	public Double tugvar;
	public Double tug;
	public Double gnt;
	
	public Departure(Date atd, Double departTugOut, 
			String cargo, int cargoWeight) {
		this.atd = atd;
		this.departTugOut = departTugOut;
		this.cargo = cargo;
		this.cargoWeight = cargoWeight;
		this.additional = new ArrayList();
	}
	
	public Departure addAdditional(String name, Date date, 
			Double cost) {
		Additional newAdd = new Additional(name, date, cost);
        this.additional.add(newAdd);
        return this;
	}

}
