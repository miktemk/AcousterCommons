package org.acouster.android.calendar;

import java.util.Calendar;

public class MyCalendarEvent
{
	public String title;
	public Calendar start, end;
	public MyCalendarEvent(String title, Calendar start, Calendar end) {
		super();
		this.title = title;
		this.start = start;
		this.end = end;
	}
	
}
