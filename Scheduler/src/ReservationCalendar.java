import java.util.Calendar;
import java.util.Date;

public class ReservationCalendar {
	
	protected int selectedWeekNumber;

	public ReservationCalendar() {
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTimeInMillis(new Date().getTime());
		this.selectedWeekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
	}
	
	public int getSelectedWeekNumber() {
		return this.selectedWeekNumber;
	}
	
	public String getSelectedWeekNumberText() {
		return "Viikko: " + this.selectedWeekNumber;
	}
	
	public void offSetSelectedWeekNumber(int offSet) {
		this.selectedWeekNumber += offSet;
	}

}
