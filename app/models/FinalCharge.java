package models;

import java.util.Date;
import java.util.List;

import play.modules.morphia.Model;

import com.google.code.morphia.annotations.Embedded;

@Embedded
public class FinalCharge extends Model {
	
	public Double actualTugIn;
	
	public Double actualTugOut;
	
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
	public Double service;
	
	public FinalCharge(Double actualTugIn, Double actualTugOut,
			String cargo, int cargoWeight) {
		this.actualTugIn = actualTugIn;
		this.actualTugOut = actualTugOut;
		this.cargo = cargo;
		this.cargoWeight = cargoWeight;
	}
	
	public FinalCharge addAdditional(String name, Double cost) {
		Additional newAdd = new Additional(name, cost);
        this.additional.add(newAdd);
        return this;
	}
}
