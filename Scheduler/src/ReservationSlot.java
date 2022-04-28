import java.util.Date;

public class ReservationSlot {
	public Date date;
	public String name = "";
	
	
	public ReservationSlot(Date d) {
		this.date = d;
	}

	public void setName(String name) {
		this.name = name;
	}
}
