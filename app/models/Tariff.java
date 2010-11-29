package models;

import java.util.*;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;

import play.modules.morphia.Model;

@Embedded
public class Tariff extends Model {
	
	public String type;
	
	public double harbor;
	
	public double queue;
	
	public double pilotfix;
	
	public double pilotvar;
	
	@Embedded
	public List<Tug> tug;
	
	public double light;
	
	public double gnt;
	
	public Tariff(String type, double harbor, double queue, double pilotfix, 
			double pilotvar, double light, double gnt) {
		this.type = type;
		this.harbor = harbor;
		this.queue = queue;
		this.pilotfix = pilotfix;
		this.pilotvar = pilotvar;
		this.tug = new ArrayList<Tug>();
		this.light = light;
		this.gnt = gnt;
	}
	
	public Tariff(String type, double harbor, double queue, double pilotfix, 
			double pilotvar, List<Tug> tug, double light, double gnt) {
		this.type = type;
		this.harbor = harbor;
		this.queue = queue;
		this.pilotfix = pilotfix;
		this.pilotvar = pilotvar;
		this.tug = tug;
		this.light = light;
		this.gnt = gnt;
	}
	
	public Tariff addTugTariff(int minimum, int maximum, double fixed, double var) {
		Tug newTug = new Tug(minimum, maximum, fixed, var);
        this.tug.add(newTug);
        return this;
	}

}
