import java.util.ArrayList;
import java.util.Date;

public class Reservations extends Storage {
	
	Reservation pendingReservation;

	public Reservations() {
		super();
		this.initStorage();
	}
	
	public ArrayList<Reservation> getAllReservations() {
		ArrayList<Reservation> r = new ArrayList<Reservation>();
		for(CacheObject co : this.getItems()) {
			Reservation reservation = new Reservation(co);
			r.add(reservation);
		}
		return r;
	}
	
	public void removeAll() {
		try {
			this.emptyStorage();
			this.emptyCache();
		}catch(Exception e) {
			System.out.println("Cant empty storage");
		}
	}
	
	public void addReservation(Reservation r) {
		this.addItem(r.itemToCacheObject());
	}
	
	public void createPendingReservation(Date t) {
		this.pendingReservation = new Reservation("",t,"");
	}
	
	public void setPendingReservationTitle(String title) {
		if(this.pendingReservation != null){
			this.pendingReservation.setTitle(title);
		}
	}
}
