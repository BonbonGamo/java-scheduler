import java.sql.Timestamp;
import java.util.Date;
import java.util.Scanner;

public class TerminalControl {
	public static void start(Reservations reservations) {
		Scanner userInput = new Scanner(System.in);
		boolean waitInput = true;
		while(waitInput) {
		        System.out.println("Started!\n\n");
		        System.out.println("Commands:");
		        System.out.println("Quit:  Q");
		        System.out.println("Empty: E");
		        System.out.println("Add:   add --name=[name]");
		        String input = userInput.nextLine();
		        if (input.equals("Q")) {
		            // Handle input
		        	System.out.println("Quitting service");
		        	waitInput = false;
		        	userInput.close();
		        }
		        if (input.equals("E")) {
		            // Handle input
		        	System.out.println("Empty storage");
		        	reservations.removeAll();
		        }
		        if (input.contains("add")) {
		            // Handle input
		        	String[] command = input.split("--name=");
		        	if(command[1] != null) {
		        		Reservation r = new Reservation(command[1], new Date(),"Kakku");
		        		reservations.addReservation(r);
		        		System.out.println("Added: " + command[1]);
		        	}else {
		        		System.out.println("Not added, missing data.");
		        	}
		        }
		    }
	}
	

}
