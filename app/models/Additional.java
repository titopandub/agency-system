package models;

import java.util.Date;

import com.google.code.morphia.annotations.Embedded;

import play.modules.morphia.Model;

@Embedded
public class Additional extends Model {
	
	public String name;
	
	public Date date;
	
	public Double cost;
	
	public Additional(String name, Double cost) {
		this.name = name;
		this.cost = cost;
	}
	
	public String toString() {
		return name;
	}
	
}
