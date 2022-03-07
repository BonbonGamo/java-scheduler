import javax.swing.JFrame;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.Date;
import java.awt.event.ActionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;

public class ReservationView {

	protected Shell selectTimeShell;
	Reservations r;
	protected ReservationCalendar reservationCalendar;
	
	public ReservationView(Reservations r) {
		this.r = r;
		this.reservationCalendar = new ReservationCalendar();
		System.out.println("ITS WEEEK: " + this.reservationCalendar.getSelectedWeekNumber());
		selectTimeShell = new Shell();
		
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		timesView();
		selectTimeShell.open();
		selectTimeShell.layout();
		while (!selectTimeShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 * @wbp.parser.entryPoint
	 */
	protected void timesView() {
		selectTimeShell.layout(true, true);
		selectTimeShell.setText("Reservation");
		Label label = new Label(selectTimeShell, SWT.NONE);
		label.setBounds(400, 15, 59, 14);
		label.setText(this.reservationCalendar.getSelectedWeekNumberText());
		
		this.appendButtons(selectTimeShell);
		this.addWeekControl(selectTimeShell, label);
	}
	
	protected void infoView() {
		
	}
	
	private void addWeekControl(Shell s, Label l) {
		int width = s.getSize().x;
		int btnWidth = 200;
		int btnHeight = 40;
		int top = 15;
		int xPad = 15;
		Button prevBtn = new Button(s, SWT.NONE);
		prevBtn.setBounds(xPad, top, btnWidth, btnHeight);
		prevBtn.addListener(SWT.Selection,  new WeekOffSetListener(this.reservationCalendar,Offset.SUBSTRACT,l));
		prevBtn.setText(" < Edellinen viikko");
		prevBtn.setBackground(new Color(95, 140, 212));
		Button nextBtn = new Button(s, SWT.NONE);
		nextBtn.setBounds(width - xPad - btnWidth, top, btnWidth, btnHeight);
		nextBtn.addListener(SWT.Selection, new WeekOffSetListener(this.reservationCalendar,Offset.ADD,l));
		nextBtn.setText("Seuraava viikko > ");
		nextBtn.setBackground(new Color(95, 140, 212));
	}
	
	private void appendButtons(Shell s) {
		System.out.println(s.getSize().toString());
		int width = s.getSize().x;
		int btnW = (width / 7) - 2;
		int btnH = 36;
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 16; j++) {
				Button btn = new Button(s, SWT.NONE);
				btn.addListener(SWT.Selection, new SelectTimeListener(this.r, new Date()));
				btn.setBounds(4 + (i * btnW), 60 + (j * btnH) + 1, btnW, btnH);
				btn.setText((j + 6) + " : 00");
				btn.setBackground(new Color( 114, 194, 136 ));
			}
		}
	}
}

class SelectTimeListener implements Listener {
	Reservations reservations;
	Date freeTime;
	public SelectTimeListener(Reservations r, Date t) {
		this.reservations = r;
		this.freeTime = t;
	}
	public void handleEvent(Event e) {
		this.reservations.addReservation(new Reservation("Kukko", this.freeTime, "kaukku"));
	}
	
}

enum Offset {
	ADD, SUBSTRACT
}

class WeekOffSetListener implements Listener {
	protected ReservationCalendar reservationCalendar;
	protected Offset offset;
	protected Label label;
	
	public WeekOffSetListener(ReservationCalendar rc, Offset offset, Label l) {
		this.reservationCalendar = rc;
		this.offset = offset;
		this.label = l;
	}
	public void handleEvent(Event e) {
		if(this.offset == Offset.ADD) {
			this.reservationCalendar.offSetSelectedWeekNumber(1);
			this.updateLabel();
		}else {
			this.reservationCalendar.offSetSelectedWeekNumber(-1);
			this.updateLabel();
		}
	}
	private void updateLabel() {
			this.label.setText(this.reservationCalendar.getSelectedWeekNumberText());
			this.label.getParent().layout();
	}
}
