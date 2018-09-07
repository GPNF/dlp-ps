package app.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import app.constants.Constants;

/**
 * @author AmolPol, AdarshSinghal
 *
 */
public class DateUtility {

	/**
	 * Return formatted date for default timezone &amp; format
	 * 
	 * @return formatted date
	 */
	public static String getCurrentTimestamp() {
		return getCurrentTimestamp(Constants.APPLICATION_TIMEZONE, Constants.APPLICATION_DATE_FORMAT);
	}

	/**
	 * Return formatted date for default date format but user provided timezone
	 * 
	 * @param timeZone
	 * @return formatted date
	 */
	public static String getCurrentTimestamp(String timeZone) {
		return getCurrentTimestamp(timeZone, Constants.APPLICATION_DATE_FORMAT);
	}

	/**
	 * Return formatted date for user provided timezone &amp; date format
	 * 
	 * @param timeZone
	 * @param format
	 * @return formatted date
	 */
	public static String getCurrentTimestamp(String timeZone, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);		
		formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
		Date date = new Date();
		String formattedTime = formatter.format(date);
		return formattedTime;
	}
	
	

	/**
	 * Converts formatted date into Timestamp for default Date Format and Timestamp
	 * 
	 * @param formattedDate
	 * @return Timestamp
	 * @throws ParseException
	 */
	public static Timestamp getTimestamp(String formattedDate) throws ParseException {
		SimpleDateFormat formatter = getDefaultFormatter();
		Date date = formatter.parse(formattedDate);
		Timestamp publishTime = new Timestamp(date.getTime());
		return publishTime;
	}
	
	/**
	 *  Converts Timestamp into formatted date
	 * 
	 * @param timestamp
	 * @return formatted date
	 */
	public static String getFormattedTime(Timestamp timestamp) {
		Date publishTime = new Date(timestamp.getTime());
		SimpleDateFormat formatter = getDefaultFormatter();
		return formatter.format(publishTime);
	}

	/**
	 * Returns SimpleDateFormat for the default TimeZone &amp; Date Format
	 * 
	 * @return SimpleDateFormat
	 */
	private static SimpleDateFormat getDefaultFormatter() {
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.APPLICATION_DATE_FORMAT);
		formatter.setTimeZone(TimeZone.getTimeZone(Constants.APPLICATION_TIMEZONE));
		return formatter;
	} 

}
