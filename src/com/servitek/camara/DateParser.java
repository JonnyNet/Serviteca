package com.servitek.camara;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.net.ParseException;

public class DateParser {
	
public final static String dateFormat = "yyyy-MM-dd HH:mm:ss.SSSZ";
	
	public final static TimeZone utc = TimeZone.getTimeZone("UTC");
	
	/**
	 * Converts a Date object to a string representation.
	 * @param date
	 * @return date as String
	 */
	public static String dateToString(Date date) {
		if (date == null) {
			return null;
		} else {
			SimpleDateFormat df = new SimpleDateFormat(dateFormat);
			df.setTimeZone(utc);
			return df.format(date);	
		}
	}
	
	/**
	 * Converts a string representation of a date to its Date object.
	 * @param dateAsString
	 * @return Date
	 */
	public static Date stringToDate(String dateAsString) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(dateFormat);
			df.setTimeZone(utc);
			return df.parse(dateAsString);
		} catch (ParseException e) {
			return null;
		} catch (NullPointerException e) {
			return null;
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
