import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import models.Agent;
import models.Customer;
import models.Operational;
import models.Owner;
import models.Port;
import models.Tariff;
import models.User;
import models.Vessel;
import play.jobs.*;
import play.modules.morphia.utils.MorphiaFixtures;
import play.test.Fixtures;

@OnApplicationStart
public class Bootstrap extends Job {
	
	public void doJob() throws Exception {
		//MorphiaFixtures.deleteAll();
		if(User.count() == 0) {
			new User("tito", "nindia", "yamatokusanagi@gmail.com").save();
			new Owner("COSCON", "Shanghai", "owner@coscon.com").save();
	    	Owner coscon = Owner.find("byName", "COSCON").first();
	    	
	    	new Vessel("YJH", "Yang Jiang He", 100000, coscon).save();
	    	Vessel vsl = Vessel.find("byName", "Yang Jiang He").first();   	
	    	
	    	Tariff cost = new Tariff("A", 0.3, 0.3, 50.0, 0.3, 0.2, 1.0);
	    	cost.addTugTariff(1, 3500, 145.0, 0.004);
	    	cost.addTugTariff(3501, 8000, 375.0, 0.004);
	    	cost.addTugTariff(8001, 14000, 570.0, 0.004);
	    	cost.addTugTariff(14001, 18000, 770.0, 0.004);
	    	cost.addTugTariff(18001, 26000, 1220.0, 0.004);
	    	cost.addTugTariff(26001, 40000, 1220.0, 0.004);
	    	cost.addTugTariff(40001, 75000, 1300.0, 0.004);
	    	cost.addTugTariff(75001, 9999999, 1700.0, 0.002);
	    	new Port("IDJAK", "Jakarta", cost).save();
	    	Port jkt = Port.find("byName", "Jakarta").first();
	    	
	    	new Agent("OGS", "Jakarta", "agency@cosco-ogs.com").save();
	    	Agent agent = Agent.find("byEmail", "agency@cosco-ogs.com").first();
	    	new Agent("OGSTPK", "Jakarta", "ogstpk@cosco-ogs.com").save();
	    	Agent subAgent = Agent.find("byEmail", "ogstpk@cosco-ogs.com").first();
	    	new Customer("GPI", "Jakarta", "test@gpi-g.com").save();
	    	Customer customer = Customer.find("byName", "GPI").first();
	    	
	    	new Operational(vsl, "249 N", jkt, customer, agent, "General", subAgent).save();
	    	Operational agc001 = Operational.find("byVoyage", "249 N").first();
	    	
	    	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	    	Date eta = df.parse("02/12/2010");
	    	Date etd = df.parse("03/12/2010");
			
	    	agc001.oBooking(eta, etd, 2.0, 2.0, "Bauxite", 20000);
	    	agc001.save();
	    	
	//    	Date ata = df.parse("14/11/2010");
	//    	etd = df.parse("21/11/2010");
	//    	
	//    	agc001.oBerthing(ata, etd, 2.0, "Bauxite", 20000);
	//    	agc001.save();
	//    	
	//    	Date atd = df.parse("21/11/2010");
	//    	
	//    	agc001.oDeparture(atd, 2.0, "Bauxite", 25000);
	//    	agc001.save();
	//    	agc001.oFinalCharge(2.0, 2.0, "Bauxite", 21000);
	//    	agc001.save();
		}
	}

}
