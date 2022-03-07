import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.sql.Timestamp;

public class Main {
	
	@SuppressWarnings("unused")
	public static void main(String[] args)  {
		Reservations reservations = new Reservations();
		if(true) {
			ReservationView rv = new ReservationView(reservations);
			rv.open();
		}else {
			TerminalControl.start(reservations);
		}
	}

}
