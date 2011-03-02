package models;

import java.util.*;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;

import play.modules.morphia.Model;

@Embedded
public class Tariff extends Model {
	
	public String type;
	
	public double harbour;
	
	public double quay;
	
	public double pilotfix;
	
	public double pilotvar;
	
	@Embedded
	public List<Tug> tug;
	
	public List<Additional> additional;
	
	public double light;
	
	public Tariff(String type, double harbour, double quay, double pilotfix, 
			double pilotvar, double light) {
		this.type = type;
		this.harbour = harbour;
		this.quay = quay;
		this.pilotfix = pilotfix;
		this.pilotvar = pilotvar;
		this.tug = new ArrayList<Tug>();
		this.additional = new ArrayList<Additional>();
		this.light = light;
	}
	
	public Tariff(String type, double harbour, double quay, double pilotfix, 
			double pilotvar, List<Tug> tug, double light) {
		this.type = type;
		this.harbour = harbour;
		this.quay = quay;
		this.pilotfix = pilotfix;
		this.pilotvar = pilotvar;
		this.tug = tug;
		this.light = light;
	}
	
	public Tariff(String type, double harbour, double quay, double pilotfix, 
			double pilotvar, List<Tug> tug, List<Additional> additional, double light) {
		this.type = type;
		this.harbour = harbour;
		this.quay = quay;
		this.pilotfix = pilotfix;
		this.pilotvar = pilotvar;
		this.tug = tug;
		this.additional = additional;
		this.light = light;
	}
	
	public Tariff addTugTariff(int minimum, int maximum, double fixed, double var) {
		Tug newTug = new Tug(minimum, maximum, fixed, var);
        this.tug.add(newTug);
        return this;
	}
	
	public Tariff addAdditional(String name, Double cost) {
		Additional newAdd = new Additional(name, cost);
        this.additional.add(newAdd);
        return this;
	}

}
