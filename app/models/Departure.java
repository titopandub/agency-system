package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import play.modules.morphia.Model;

import com.google.code.morphia.annotations.Embedded;

@Embedded
public class Departure extends Model {
	
	public Date atd;
	
	public int quay;
	
	public Double departTugOut;
	
	public String cargo;
	
	public int cargoWeight;
	
	@Embedded
	public List<Additional> additional;
	
	//Calculation
	public Double harbor;
	public Double queue;
	public Double pilot;
	public Double specialpilot;
	public Double light;
	public Double tugfix;
	public Double tugvar;
	public Double tug;
	public Double gnt;
	public Double service;
	
	public Departure(Date atd, int quay, Double departTugOut,
			String cargo, int cargoWeight) {
		this.atd = atd;
		this.quay = quay;
		this.departTugOut = departTugOut;
		this.cargo = cargo;
		this.cargoWeight = cargoWeight;
		this.additional = new ArrayList();
	}
	
	public Departure addAdditional(String name, Double cost) {
		Additional newAdd = new Additional(name, cost);
        this.additional.add(newAdd);
        return this;
	}

}
