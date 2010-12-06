package controllers;

import java.util.ArrayList;
import java.util.List;

import play.mvc.Controller;

import models.Port;
import models.Tariff;
import models.Tug;

public class Ports extends CRUD {
	
	public static void show(Long id) {
		if(id != null) {
			Port port = Port.findById(id);
			render(port);
		}
		render();
	}
	
	public static void save(Long id, String code, String name, String type, 
			double harbor, double queue, double pilotfix, double pilotvar, 
			double light, double gnt, List<Tug> tug) {
		Port port;
		if(id != null) {
	    	port = Port.findById(id);
			port.code = code;
			port.name = name;
			port.costtariff.type = type;
			port.costtariff.harbor = harbor;
			port.costtariff.queue = queue;
			port.costtariff.pilotfix = pilotfix;
			port.costtariff.pilotvar = pilotvar;
			port.costtariff.light = light;
			port.costtariff.gnt = gnt;
			port.costtariff.tug = new ArrayList<Tug>();
			int i = 0;
			while(i < tug.size()) {
				port.costtariff.addTugTariff(tug.get(i).minimum, 
						tug.get(i).maximum, tug.get(i).fixed, tug.get(i).var);
				i++;
			}
		} else {
			Tariff cost = new Tariff(type, harbor, queue, pilotfix, pilotvar, 
					light, gnt);
			cost.tug = new ArrayList<Tug>();
			int i = 0;
			while(i < tug.size()) {
				cost.addTugTariff(tug.get(i).minimum, 
						tug.get(i).maximum, tug.get(i).fixed, tug.get(i).var);
				i++;
			}
	    	port = new Port(code, name, cost);
		}
		
		port.save();
        redirect(request.controller + ".list");
	}
	
}
