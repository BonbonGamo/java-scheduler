import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.sql.Timestamp;

public class Main {
	
	@SuppressWarnings("unused")
	public static void main(String[] args)  {
		Reservations reservations = new Reservations();
		TerminalControl.start(reservations);
	}

}
