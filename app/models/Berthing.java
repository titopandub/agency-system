package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.code.morphia.annotations.Embedded;

import play.modules.morphia.Model;

@Embedded
public class Berthing extends Model {
	
	public Date ata;
	
	public Date etd;
	
	public int quay;
	
	public Double berthTugIn;
	
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
	public Double tug;
	public Double gnt;
	public Double service;
	
	public Berthing(Date ata, Date etd, int quay, Double berthTugIn,
			String cargo, int cargoWeight) {
		this.ata = ata;
		this.etd = etd;
		this.quay = quay;
		this.berthTugIn = berthTugIn;
		this.cargo = cargo;
		this.cargoWeight = cargoWeight;
		this.additional = new ArrayList();
	}
	
	public Berthing addAdditional(String name, Double cost) {
		Additional newAdd = new Additional(name, cost);
        this.additional.add(newAdd);
        return this;
	}

}
