import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


enum ProgramStatus {
	SHUT_DOWN,
	MAIN_PAGE,
	SELECT_TIME,
	GIVE_NAME
}

public class TerminalControl {
	
	private static String spacer = "-------------------------------------------------------------";
	
	private static void printMainPageInfo () {
        System.out.println(spacer);
        System.out.println("1) Varaa aika 2) Näytä varaukset Q) Lopeta E) Tyhjennä muisti");
	}

	
	public static void start(Reservations reservations) {
		ReservationCalendar rc = new ReservationCalendar(reservations);
		Scanner userInput = new Scanner(System.in);
		ProgramStatus status = ProgramStatus.MAIN_PAGE;
		while(status != ProgramStatus.SHUT_DOWN) {
		        if(status == ProgramStatus.MAIN_PAGE) {
		        	TerminalControl.printMainPageInfo();
		        	String input = userInput.nextLine();
		        	if (userInput.equals("Q")) {
		        		System.out.println("Suljetaan");
	        			status = ProgramStatus.SHUT_DOWN;
	        			userInput.close();
	        		}
		        	if (input.equals("E")) {
		        		System.out.println("Tyhjennetään muisti");
		        		reservations.removeAll();
		        	}

		        	if (input.equals("1")) {
		        		status = ProgramStatus.SELECT_TIME;
		        		System.out.println(spacer);
		        		System.out.println("0-6) Valitse aika S) Seuraava päivä E) Edellinen päivä");
		        		System.out.println("Valitse aika:");
		        		ArrayList<ReservationSlot> times = rc.getDayTimes();
			        	for(int i = 0; i < times.size();i++) {
			        		System.out.println("" + i + ") " + times.get(i).date.toString());
			        	}
		        	}

		        	if (input.equals("2")) {
			        	ArrayList<Reservation> rs = reservations.getAllReservations();
			        	if(rs.size() == 0) {
			        		System.out.println("Ei varauksia");
			        	}
			        	for(int i = 0; i < rs.size(); i++) {
			        		System.out.println("(" + i + ") " + rs.get(i).getInfo());
			        	}
		        	}
		        }
		        if(status == ProgramStatus.SELECT_TIME) {
		        	String input = userInput.nextLine();
		        	if(input.equals("S")) {
		        		rc.offSetDay(1);
		        		ArrayList<ReservationSlot> times = rc.getDayTimes();
			        	for(int i = 0; i < times.size();i++) {
			        		System.out.println("" + i + ") " + times.get(i).date.toString());
			        	}
		        	} else if(input.equals("E")) {
		        		rc.offSetDay(-1);
		        		ArrayList<ReservationSlot> times = rc.getDayTimes();
			        	for(int i = 0; i < times.size();i++) {
			        		System.out.println("" + i + ") " + times.get(i).date.toString());
			        	}
		        		
		        	} else {
		        		try {
		        			boolean timeSelected = rc.setSelectedSlot(Integer.parseInt(input,10));
		        			if(timeSelected) {
		        				status = ProgramStatus.GIVE_NAME;
		        				System.out.println("Kirjoita nimesi varausta varten ja paina enter Q) Peruuta");
		        			}
		        		} catch(Exception e) {
		        			System.out.println("Tuntematon komento");
		        		}
		        	}
		        }
		        if(status == ProgramStatus.GIVE_NAME) {
		        	String input = userInput.nextLine();
		        	if(input.length() < 3) {
		        		System.out.println("Anna pidempi nimi");
		        	}else if(input.equals("Q")) {
		        		status = ProgramStatus.MAIN_PAGE;
		        	}else {
		        		rc.makeReservationWithName(input);
		        		System.out.println("Varaus tehty! Paina entteriä jatkaaksesi");
		        		status = ProgramStatus.MAIN_PAGE;
		        	}
		        }
		    }
	}
	
	
}

