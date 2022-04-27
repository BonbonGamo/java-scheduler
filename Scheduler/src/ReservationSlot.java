import java.util.Date;

public class ReservationSlot {
	public Date date;
	public boolean reserved = false;
	public String name = "";
	
	
	public ReservationSlot(Date d) {
		this.date = d;
	}

	public ReservationSlot(Date d, boolean reserved, String name) {
		this.date = d;
		this.reserved = reserved;
		this.name = name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
