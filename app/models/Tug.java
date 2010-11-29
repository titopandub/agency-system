package models;

import play.modules.morphia.Model;

import com.google.code.morphia.annotations.Embedded;

@Embedded
public class Tug extends Model {
	
	public Integer minimum;
	
	public Integer maximum;
	
	public double fixed;
	
	public double var;
	
	public Tug(int minimum, int maximum, double fixed, double var) {
		this.minimum = minimum;
		this.maximum = maximum;
		this.fixed = fixed;
		this.var = var;
	}
	
	public String toString() {
		return maximum.toString();
	}

}
