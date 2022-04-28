import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReservationCalendar {
	private Date[] daySlots;
	private int dayOffSet = 1;
	private Reservations reservations;
	ArrayList<ReservationSlot> slots;
	ReservationSlot selectedSlot;
	
	private void setSlotsByDateOffSet() {
		Date now = new Date();
		Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.DATE,this.dayOffSet);
        c.set(Calendar.HOUR_OF_DAY, 9);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        c.set(Calendar.MILLISECOND, 0);
		this.slots = new ArrayList<ReservationSlot>();
		for( int i = 0; i < 7; i++ ) {
			c.add(Calendar.HOUR, 1);
			this.slots.add(new ReservationSlot(c.getTime()));
		}
	}
	
	public ReservationCalendar (Reservations reservations) {
		this.reservations = reservations;
		this.setSlotsByDateOffSet();
		selectedSlot = this.slots.get(0);
	}
	
	public void offSetDay(int days) {
		int newOffSet = this.dayOffSet + days;
		if(newOffSet < 1) {
			System.out.println("Voit tehdä varauksen aikaisintaan huomiselle");
			return;
		}
		this.dayOffSet = newOffSet;
		this.setSlotsByDateOffSet();
		selectedSlot = this.slots.get(0);
	}
	
	public ArrayList<ReservationSlot> getDayTimes() {
		ArrayList<ReservationSlot> slotsToShow = new ArrayList<ReservationSlot>();
		for(ReservationSlot slot : slots) {
			if(this.reservations.hasCollidinSlot(slot.date) == false) {
				slotsToShow.add(slot);
			}
		}
		if(slotsToShow.size() < 1) {
			System.out.println("Ei vapaita aikoja tälle päivälle. Valitse seuraava päivä!");
		}
		this.slots = slotsToShow;
		return this.slots;
	}
	
	public boolean setSelectedSlot(int i) {
		if(i > this.slots.size() - 1) {
			System.out.println("Tätä aikaa ei ole valittavissa. Yritä uudelleen");
			return false;
		}else {
			System.out.println("Aika valittu!");
			this.selectedSlot = slots.get(i);
			return true;
		}
	}
	
	public void makeReservationWithName(String name) {
		Reservation newReservation = new Reservation("Varaus", this.selectedSlot.date, name);
		this.reservations.addReservation(newReservation);
	}

}
