package org.acouster.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class DateHelpers
{
	public static Calendar now()
	{
		//Calendar now = Calendar.getInstance(TimeZone.getTimeZone("EST"));
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		// America/New_York
		return now;
	}
	public static boolean between(Calendar x, Calendar start, Calendar end)
	{
		return x.after(start) && x.before(end);
	}
	public static Calendar getTimeOfDay(Calendar x)
	{
		Calendar c = now();
		c.clear();
		c.set(Calendar.HOUR_OF_DAY, x.get(Calendar.HOUR_OF_DAY));
		c.set(Calendar.MINUTE, x.get(Calendar.MINUTE));
		return c;
	}
	public static Calendar getMonday()
	{
		return getMonday(now());
	}
	public static Calendar getMonday(Calendar date)
	{
		Calendar c = now();
		c.clear();
		c.set(
				date.get(Calendar.YEAR),
				date.get(Calendar.MONTH),
				date.get(Calendar.DATE),
				0,
				0,
				0
				);
		TimeZone ttt = c.getTimeZone();
		int diff = getDayOfWeekIndex(c);
		if (diff != -1)
			c.add(Calendar.DAY_OF_WEEK, -diff);
		return c;
	}
	public static int getDayOfWeekIndex(Calendar timeInWeek)
	{
		int ddd = timeInWeek.get(Calendar.DAY_OF_WEEK);
		switch (ddd)
		{
		case Calendar.MONDAY:
			return 0;
		case Calendar.TUESDAY:
			return 1;
		case Calendar.WEDNESDAY:
			return 2;
		case Calendar.THURSDAY:
			return 3;
		case Calendar.FRIDAY:
			return 4;
		case Calendar.SATURDAY:
			return 5;
		case Calendar.SUNDAY:
			return 6;
		}
		return -1;
	}
	public static Calendar getNextMonday()
	{
		Calendar mon = getMonday(now());
		mon.add(Calendar.DAY_OF_WEEK, 7);
		return mon;
	}
	
	public static Calendar compose(Calendar date, Calendar time)
	{
		Calendar c = now();
		c.clear();
		c.set(
				date.get(Calendar.YEAR),
				date.get(Calendar.MONTH),
				date.get(Calendar.DATE),
				time.get(Calendar.HOUR_OF_DAY),
				time.get(Calendar.MINUTE),
				time.get(Calendar.SECOND)
				);
		return c;
	}
	public static Calendar makeCopy(Calendar date)
	{
		Calendar c = now();
		c.clear();
		c.set(
				date.get(Calendar.YEAR),
				date.get(Calendar.MONTH),
				date.get(Calendar.DATE),
				date.get(Calendar.HOUR_OF_DAY),
				date.get(Calendar.MINUTE),
				date.get(Calendar.SECOND)
				);
		return c;
	}
	public static long dateDiffMillis(Calendar x1, Calendar x2)
	{
		long ms1 = x1.getTimeInMillis();
		long ms2 = x2.getTimeInMillis();
		return ms2-ms1;
	}
	
	public static Calendar parseTime(String txt)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		Calendar c = now();
		c.clear();
		try {
			c.setTime(formatter.parse(txt));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return c;
	}
	public static long parseTimeGetMillis(String txt)
	{
		Calendar ccc = parseTime(txt);
		return ccc.getTimeInMillis() - 18000000; // mystery 5 hours...?
	}
	public static Calendar min(Calendar x1, Calendar x2)
	{
		if (x2.before(x1))
			return x2;
		return x1;
	}
	public static Calendar max(Calendar x1, Calendar x2)
	{
		if (x2.after(x1))
			return x2;
		return x1;
	}
}
