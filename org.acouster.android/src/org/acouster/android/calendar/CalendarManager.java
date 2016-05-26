package org.acouster.android.calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.acouster.util.DateHelpers;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

public class CalendarManager
{
	Activity context;
	public CalendarManager(Activity context)
	{
		this.context = context;
	}
	
	private MyCalendar[] m_calendars;
	public MyCalendar[] getCalendars() {
    	String[] l_projection = new String[]{"_id"};
    	Uri l_calendars;
    	if (Build.VERSION.SDK_INT >= 8) {
    		l_calendars = Uri.parse("content://com.android.calendar/calendars");
    	} else {
    		l_calendars = Uri.parse("content://calendar/calendars");
    	}
    	Cursor l_managedCursor = context.managedQuery(l_calendars, l_projection, null, null, null);	//all calendars
    	//Cursor l_managedCursor = this.managedQuery(l_calendars, l_projection, "selected=1", null, null);   //active calendars
    	if (l_managedCursor.moveToFirst()) {
    		m_calendars = new MyCalendar[l_managedCursor.getCount()];
    		String l_calName;
    		String l_calId;
    		int l_cnt = 0;
    		//int l_nameCol = l_managedCursor.getColumnIndex(l_projection[1]);
    		int l_idCol = l_managedCursor.getColumnIndex(l_projection[0]);
    		do {
    			//l_calName = l_managedCursor.getString(l_nameCol);
    			l_calName = "calendar display name";
    			l_calId = l_managedCursor.getString(l_idCol);
    			m_calendars[l_cnt] = new MyCalendar(l_calName, l_calId);
    			++l_cnt;
    		} while (l_managedCursor.moveToNext());
    		return m_calendars;
    	}
    	return null;
    }
	
	public List<MyCalendarEvent> getLastThreeEvents(String m_CalendarId) {
    	Uri l_eventUri;
    	if (Build.VERSION.SDK_INT >= 8) {
    		l_eventUri = Uri.parse("content://com.android.calendar/events");
    	} else {
    		l_eventUri = Uri.parse("content://calendar/events");
    	}
    	String[] l_projection = new String[]{"title", "dtstart", "dtend"};
    	Cursor l_managedCursor = context.managedQuery(l_eventUri, l_projection, "calendar_id=" + m_CalendarId, null, "dtstart DESC, dtend DESC");
    	//Cursor l_managedCursor = context.managedQuery(l_eventUri, l_projection, null, null, null);
    	List<MyCalendarEvent> result = new Vector<MyCalendarEvent>();
    	if (l_managedCursor.moveToFirst()) {
    		int l_cnt = 0;
    		String l_title;
    		Calendar l_begin;
    		Calendar l_end;
    		//StringBuilder l_displayText = new StringBuilder();
    		int l_colTitle = l_managedCursor.getColumnIndex(l_projection[0]);
    		int l_colBegin = l_managedCursor.getColumnIndex(l_projection[1]);
    		int l_colEnd = l_managedCursor.getColumnIndex(l_projection[1]);
    		do {
    			l_title = l_managedCursor.getString(l_colTitle);
    			l_begin = getDateTimeCalendar(l_managedCursor.getString(l_colBegin));
    			l_end = getDateTimeCalendar(l_managedCursor.getString(l_colEnd));
    			//l_displayText.append(l_title + "\n" + l_begin + "\n" + l_end + "\n----------------\n");
    			result.add(new MyCalendarEvent(l_title, l_begin, l_end));
    			++l_cnt;
    		} while (l_managedCursor.moveToNext() && l_cnt < 3);
    	}
    	return result;
    }
	
	public List<MyCalendarEvent> getEventsForWeek(String m_CalendarId, Calendar monday)
	{
		Calendar nextMonday = DateHelpers.makeCopy(monday);
		nextMonday.add(Calendar.DAY_OF_WEEK, 7);
		
		Uri l_eventUri;
    	if (Build.VERSION.SDK_INT >= 8) {
    		l_eventUri = Uri.parse("content://com.android.calendar/events");
    	} else {
    		l_eventUri = Uri.parse("content://calendar/events");
    	}
    	String[] l_projection = new String[]{"title", "dtstart", "dtend"};
    	String filter = "calendar_id=" + m_CalendarId + " and dtstart>=" + monday.getTimeInMillis() + " and dtstart<" + nextMonday.getTimeInMillis();
    	//Log.v(DebugUtil.TAG, "CALEWNDARIO filter: " + filter);

    	Cursor l_managedCursor = context.managedQuery(l_eventUri, l_projection, filter, null, "dtstart ASC, dtend ASC");
    	
    	List<MyCalendarEvent> result = new Vector<MyCalendarEvent>();
    	if (l_managedCursor.moveToFirst()) {
    		String l_title;
    		Calendar l_begin;
    		Calendar l_end;
    		int l_colTitle = l_managedCursor.getColumnIndex(l_projection[0]);
    		int l_colBegin = l_managedCursor.getColumnIndex(l_projection[1]);
    		int l_colEnd = l_managedCursor.getColumnIndex(l_projection[2]);
    		do {
    			//Log.v(DebugUtil.TAG, "------------>>> " + l_managedCursor.getColumnCount());
    			//for (int i = 0; i < l_managedCursor.getColumnCount(); i++)
    			//	Log.v(DebugUtil.TAG, l_managedCursor.getColumnName(i) + ": " + l_managedCursor.getString(i));

    			l_title = l_managedCursor.getString(l_colTitle);
    			l_begin = getDateTimeCalendar(l_managedCursor.getString(l_colBegin));
    			l_end = getDateTimeCalendar(l_managedCursor.getString(l_colEnd));
    			result.add(new MyCalendarEvent(l_title, l_begin, l_end));
    		} while (l_managedCursor.moveToNext());
    	}
    	
    	return result;
    }
	
	public List<MyCalendarEvent> getDefaultCalendarEventsForWeek(Calendar monday)
	{
		MyCalendar[] cals = getCalendars();
		if (cals.length > 0)
			return getEventsForWeek(cals[0].id, monday);
		return new Vector<MyCalendarEvent>();
	}
	
	
	/************************************************
     * utility part
     */
    private static final String DATE_TIME_FORMAT = "yyyy MMM dd, HH:mm:ss";
    public static String getDateTimeStr(int p_delay_min) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
		if (p_delay_min == 0) {
			return sdf.format(cal.getTime());
		} else {
			Date l_time = cal.getTime();
			l_time.setMinutes(l_time.getMinutes() + p_delay_min);
			return sdf.format(l_time);
		}
	}
    public static String getDateTimeStr(String p_time_in_millis) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
    	Date l_time = new Date(Long.parseLong(p_time_in_millis));
    	return sdf.format(l_time);
    }
    public static Calendar getDateTimeCalendar(String p_time_in_millis) {
    	long millis = Long.parseLong(p_time_in_millis);
    	Calendar cal = Calendar.getInstance();
    	cal.clear();
    	cal.setTimeInMillis(millis);
    	return cal;
    }
    
}
