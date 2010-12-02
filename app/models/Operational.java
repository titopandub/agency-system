package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Period;

import play.modules.morphia.Model;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Reference;
import com.google.code.morphia.query.OrBuilder;
import com.google.code.morphia.query.Query;

@Entity
public class Operational extends Model {
	
	public String bNo;
	
	public String bstatus;
	
	@Embedded
	public Vessel vessel;
	
	public String voyage;
	
	@Embedded
	public Port port;
	
	@Embedded
	public Customer customer;
	
	@Embedded
	public Agent agent;
	
	public String statusAgent;
	
	@Embedded
	public Agent subAgent;
	
	@Embedded
	public Booking booking;
	
	@Embedded
	public Berthing berthing;
	
	@Embedded
	public Departure departure;
	
	@Embedded
	public FinalCharge finalCharge;
	
	public Integer tugPtr;
	public Double tugfix;
	public Double tugvar;
	
	public Operational() {
		
	}
	
	public Operational(Vessel vessel, String voyage, Port port,
			Customer customer, Agent agent, String statusAgent, 
			Agent subAgent) {
		this.vessel = vessel;
		this.voyage = voyage;
		this.port = port;
		this.customer = customer;
		this.agent = agent;
		this.statusAgent = statusAgent;
		this.subAgent = subAgent;
		this.bstatus = "New";
		this.tugPtr = this.searchIntegerArray(port.getTugTariff(), vessel.grt);
		this.tugfix = port.costtariff.tug.get(this.tugPtr).fixed;
		this.tugvar = port.costtariff.tug.get(this.tugPtr).var;
	}
	
	public void oBooking(Date eta, Date etd, Double bookTugIn, 
			Double bookTugOut, String cargo, int cargoWeight) {
		this.booking = new Booking(eta, etd, bookTugIn, bookTugOut, cargo, cargoWeight);
		this.bstatus = "New";
		this.booking.additional = new ArrayList();
		eBookingExpenses();
	}
	
	public void oBerthing(Date ata, Date etd, Double berthTugIn,
			String cargo, int cargoWeight) {
		this.berthing = new Berthing(ata, etd, berthTugIn, cargo, cargoWeight);
		this.bstatus = "Berthing";
		this.berthing.additional = booking.additional;
		eBerthingExpenses();
	}
	
	public void oDeparture(Date atd, Double departTugOut, 
			String cargo, int cargoWeight) {
		this.departure = new Departure(atd, departTugOut, cargo, cargoWeight);
		this.bstatus = "Departure";
		this.departure.additional = berthing.additional;
		eDepartureExpenses();
	}
	
	public void oFinalCharge(Double actualTugIn, Double actualTugOut,
			String cargo, int cargoWeight) {
		this.finalCharge = new FinalCharge(actualTugIn, actualTugOut, cargo, 
				cargoWeight);
		this.bstatus = "Final";
		this.finalCharge.additional = departure.additional;
		aFinalCharges();
	}
	
	public void eBookingExpenses() {
		DateTime dtEta = new DateTime(booking.eta);
		DateTime dtEtd = new DateTime(booking.etd);
		Period days = new Period(dtEta, dtEtd);
		int periodDay = days.toStandardDays().getDays();
		
		this.booking.harbor = vessel.grt * port.costtariff.harbor * periodHarbor(periodDay);
		this.booking.queue = vessel.grt * port.costtariff.queue * periodDay;
		this.booking.pilot = ((vessel.grt * port.costtariff.pilotvar) + 
				port.costtariff.pilotfix) * 2;
		this.booking.light = vessel.grt * port.costtariff.light;
		this.booking.tug = ((vessel.grt * this.tugvar) + 
				this.tugfix) * (booking.bookTugIn + booking.bookTugOut);
		this.booking.gnt = (this.booking.harbor + this.booking.queue + this.booking.pilot + 
				this.booking.tug) * 10 / 100;
		
	}
	
	public void eBerthingExpenses() {
		DateTime dtAta = new DateTime(berthing.ata);
		DateTime dtEtd = new DateTime(berthing.etd);
		Period days = new Period(dtAta, dtEtd);
		int periodDay = days.toStandardDays().getDays();
		
		this.berthing.harbor = vessel.grt * port.costtariff.harbor * periodHarbor(periodDay);
		this.berthing.queue = vessel.grt * port.costtariff.queue * periodDay;
		this.berthing.pilot = ((vessel.grt * port.costtariff.pilotvar) + 
				port.costtariff.pilotfix) * 2;
		this.berthing.light = vessel.grt * port.costtariff.light;
		this.berthing.tug = ((vessel.grt * tugvar) + 
				tugfix) * (berthing.berthTugIn + booking.bookTugOut);
		this.berthing.gnt = (this.berthing.harbor + this.berthing.queue + this.berthing.pilot + 
				this.berthing.tug) * 10 / 100;
		
	}
	
	public void eDepartureExpenses() {
		DateTime dtAta = new DateTime(berthing.ata);
		DateTime dtAtd = new DateTime(departure.atd);
		Period days = new Period(dtAta, dtAtd);
		int periodDay = days.toStandardDays().getDays();
		
		this.departure.harbor = vessel.grt * port.costtariff.harbor * periodHarbor(periodDay);
		this.departure.queue = vessel.grt * port.costtariff.queue * periodDay;
		this.departure.pilot = ((vessel.grt * port.costtariff.pilotvar) + 
				port.costtariff.pilotfix) * 2;
		this.departure.light = vessel.grt * port.costtariff.light;
		this.departure.tug = ((vessel.grt * tugvar) + tugfix) 
		* (berthing.berthTugIn + departure.departTugOut);
		this.departure.gnt = (this.departure.harbor + this.departure.queue + this.departure.pilot + 
				this.departure.tug) * 10 / 100;
		
	}
	
	public void aFinalCharges() {
		DateTime dtAta = new DateTime(berthing.ata);
		DateTime dtAtd = new DateTime(departure.atd);
		Period days = new Period(dtAta, dtAtd);
		int periodDay = days.toStandardDays().getDays();
		
		this.finalCharge.harbor = vessel.grt * port.costtariff.harbor * periodHarbor(periodDay);
		this.finalCharge.queue = vessel.grt * port.costtariff.queue * periodDay;
		this.finalCharge.pilot = ((vessel.grt * port.costtariff.pilotvar) + 
				port.costtariff.pilotfix) * 2;
		this.finalCharge.light = vessel.grt * port.costtariff.light;
		this.finalCharge.tug = ((vessel.grt * tugvar) + tugfix) 
		* (finalCharge.actualTugIn + finalCharge.actualTugOut);
		this.finalCharge.gnt = (this.finalCharge.harbor + this.finalCharge.queue + 
				this.finalCharge.pilot + this.finalCharge.tug) * 10 / 100;
		
	}
	
	public List<Operational> listBooking() {
		Query q = Operational.createQuery(); // create a Query
	    OrBuilder or = q.or(); // get Or query builder
	    or.add().field("bstatus").contains("New"); // select bstatus contains "New"
	    or.add().field("bstatus").contains("Booking Rejected"); // or bstatus contains "Booking Rejected"
	    return q.asList();
	}
	
	public List<Operational> listBerthing() {
		Query q = Operational.createQuery();
	    OrBuilder or = q.or();
	    or.add().field("bstatus").contains("Berthing");
	    or.add().field("bstatus").contains("Booking Approved");
	    or.add().field("bstatus").contains("Berthing Rejected");
	    return q.asList();
	}
	
	public List<Operational> listDeparture() {
		Query q = Operational.createQuery();
	    OrBuilder or = q.or();
	    or.add().field("bstatus").contains("Departure");
	    or.add().field("bstatus").contains("Berthing Approved");
	    or.add().field("bstatus").contains("Departure Rejected");
	    return q.asList();
	}
	
	public List<Operational> listFinal() {
		Query q = Operational.createQuery();
	    OrBuilder or = q.or();
	    or.add().field("bstatus").contains("Final");
	    or.add().field("bstatus").contains("Departure Approved");
	    or.add().field("bstatus").contains("Final Rejected");
	    return q.asList();
	}
	
	public void approvalBooking(Boolean approve) {
		if(approve) {
			this.bstatus = "Booking Approved";
		} else {
			this.bstatus = "Booking Rejected";
		}
	}
	
	public void approvalBerthing(Boolean approve) {
		if(approve) {
			this.bstatus = "Berthing Approved";
		} else {
			this.bstatus = "Berthing Rejected";
		}
	}
	
	public void approvalDeparture(Boolean approve) {
		if(approve) {
			this.bstatus = "Departure Approved";
		} else {
			this.bstatus = "Berthing Rejected";
		}
	}
	
	public void approvalFinal(Boolean approve) {
		if(approve) {
			this.bstatus = "Final Approved";
		} else {
			this.bstatus = "Final Reject";
		}
	}
	
	public Integer searchIntegerArray(Integer[] minimum, Integer grt) {
		int bot = 0;
		int top = minimum.length - 1;
		
		while(bot <= top) {
			int mid = (bot + top) / 2;
			if(grt < minimum[mid+1] && grt > minimum[mid]) return mid;
			else if(grt < minimum[mid]) top = mid - 1;
			else if(grt > minimum[mid]) bot = mid + 1;
		}
		return -1;
	}
	
	public Integer periodHarbor(int day) {
		if (day >= 1 && day <= 10) {
			return 1;
		} else if (day >= 11 && day <= 20) {
			return 2;
		} else {
			return 2;
		}
	}
	
}
