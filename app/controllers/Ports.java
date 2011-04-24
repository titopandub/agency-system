package controllers;

import java.util.ArrayList;
import java.util.List;

import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.With;

import models.Additional;
import models.Port;
import models.Tariff;
import models.Tug;

@With(Secure.class)
public class Ports extends CRUD {
	
	public static void show(Long id) {
		if(id != null) {
			Port port = Port.findById(id);
			render(port);
		}
		render();
	}
	
	public static void save(Long id, String code, String name, String info,
			String type, double harbour, double quay, double pilotfix, double pilotvar, 
			double light, double service, List<Tug> tug, List<Additional> additional) {
		Port port;
		if(id != null) {
	    	port = Port.findById(id);
			port.code = code;
			port.name = name;
			port.info = info;
			port.costtariff.type = type;
			port.costtariff.harbour = harbour;
			port.costtariff.quay = quay;
			port.costtariff.pilotfix = pilotfix;
			port.costtariff.pilotvar = pilotvar;
			port.costtariff.light = light;
			port.costtariff.service = service;
			port.costtariff.tug = new ArrayList<Tug>();
			port.costtariff.additional = new ArrayList<Additional>();
			
			int i = 0;
			while(i < tug.size()) {
				if(tug.get(i).minimum == null) tug.get(i).minimum = 0;
				if(tug.get(i).maximum == null) tug.get(i).maximum = 0;
				port.costtariff.addTugTariff(tug.get(i).minimum, 
						tug.get(i).maximum, tug.get(i).fixed, tug.get(i).var);
				i++;
			}
			i = 0;
			while(i < additional.size()) {
				port.costtariff.addAdditional(additional.get(i).name, 
						additional.get(i).cost);
				i++;
			}

		} else {
			Tariff cost = new Tariff(type, harbour, quay, pilotfix, pilotvar, 
					light, service);
			cost.tug = new ArrayList<Tug>();
			cost.additional = new ArrayList<Additional>();
			
			int i = 0;
			while(i < tug.size()) {
				if(tug.get(i).minimum == null) tug.get(i).minimum = 0;
				if(tug.get(i).maximum == null) tug.get(i).maximum = 0;
				cost.addTugTariff(tug.get(i).minimum, 
						tug.get(i).maximum, tug.get(i).fixed, tug.get(i).var);
				i++;
			}
			i = 0;
			while(i < additional.size()) {
				cost.addAdditional(additional.get(i).name, 
						additional.get(i).cost);
				i++;
			}
	    	port = new Port(name, cost);
	    	port.code = code;
	    	port.info = info;
		}
		
		port.save();
        flash.success(Messages.get("crud.created", "Port"));
        if (params.get("_save") != null) {
            redirect(request.controller + ".list");
        }
        if (params.get("_saveAndAddAnother") != null) {
            redirect(request.controller + ".blank");
        }
        redirect(request.controller + ".show", port._key());
	}
	
}
