import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Reservation implements StoreableObject<ReservationObject> {
	protected ReservationObject r;
	
	public Reservation(HashMap<String,String> o) {
		try {
			Date start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").parse(o.get("start"));
			this.r = new ReservationObject(o.get("title"),start, o.get("person"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Reservation(String title, Date start, String person) {
		this.r = new ReservationObject(title, start, person);
	}
	
	public void setTitle(String title) {
		this.r.title = title;
	}
	
	public String getInfo() {
		if(this.r == null) {
			return "Nulll";
		}
		return "" + this.r.getStart().toString() + " - " + this.r.getName();
	}
	
	public boolean isColliding(Date d) {
		return this.r.getStart().equals(d);
	}
	
	public CacheObject itemToCacheObject(){
		CacheObject hm = new CacheObject();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		hm.put("title", r.title);
		hm.put("start", "" + dateFormat.format(r.start));
		hm.put("person", r.person);
		hm.put("duration", "" + r.duration);
		return hm;
	}

}

class ReservationObject {
	protected Date start;
	protected int duration;
	protected String title;
	protected String person;
	public ReservationObject(String title, Date start, String person) {
		this.title = title;
		this.start = start;
		this.person = person;
		this.duration = 60;
	}
	public Date getStart() {
		return this.start;
	}
	public String getTitle() {
		return this.title;
	}
	public String getName() {
		return this.person;
	}
	public HashMap<String,String> toHashMap() {
		HashMap<String,String> hm = new HashMap<String,String>();
		hm.put("title", this.title);
		hm.put("duration", "" + this.duration);
		hm.put("person", this.person);
		hm.put("start", "" + this.start);
		return hm;
	}
}