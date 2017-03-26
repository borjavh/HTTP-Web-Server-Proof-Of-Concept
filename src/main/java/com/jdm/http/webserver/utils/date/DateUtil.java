package com.jdm.http.webserver.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class DateUtil {
	
	// Date format pattern used to parse HTTP date headers in RFC 1123 format.
	public static final String PATTERN_RFC1123 = "EEE, dd-MMM-yyyy HH:mm:ss zzz";

	// Date format pattern used to parse HTTP date headers in RFC 1036 format.
	public static final String PATTERN_RFC1036 = "EEEE, dd-MMM-yy HH:mm:ss zzz";

	/**
	 * Date format pattern used to parse HTTP date headers in ANSI C
	 * <code>asctime()</code> format.
	 */
	public static final String PATTERN_ASCTIME = "EEE MMM d HH:mm:ss yyyy";

	private static final Collection<String> DEFAULT_PATTERNS = Arrays
			.asList(new String[] { PATTERN_ASCTIME, PATTERN_RFC1036, PATTERN_RFC1123 });

	private static final Date DEFAULT_TWO_DIGIT_YEAR_START;

	static {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2000, Calendar.JANUARY, 1, 0, 0);
		DEFAULT_TWO_DIGIT_YEAR_START = calendar.getTime();
	}

	private static final TimeZone GMT = TimeZone.getTimeZone("GMT");
	
	private DateUtil() {
	}

	public static Date parseDate(String dateValue) {
		return parseDate(dateValue, null, null);
	}

	public static Date parseDate(String dateValue, Collection<String> dateFormats) {
		return parseDate(dateValue, dateFormats, null);
	}

	public static Date parseDate(String dateValue, Collection<String> dateFormats, Date startDate) {

		if (dateValue == null) {
			throw new IllegalArgumentException("dateValue is null");
		}
		if (dateFormats == null) {
			dateFormats = DEFAULT_PATTERNS;
		}
		if (startDate == null) {
			startDate = DEFAULT_TWO_DIGIT_YEAR_START;
		}
		
		if (dateValue.length() > 1 && dateValue.startsWith("'") && dateValue.endsWith("'")) {
			dateValue = dateValue.substring(1, dateValue.length() - 1);
		}

		SimpleDateFormat dateParser = null;
		for (String format : dateFormats) {
			if (dateParser == null) {
				dateParser = new SimpleDateFormat(format, Locale.US);
				dateParser.setTimeZone(TimeZone.getTimeZone("GMT"));
				dateParser.set2DigitYearStart(startDate);
			} else {
				dateParser.applyPattern(format);
			}
			try {
				return dateParser.parse(dateValue);
			} catch (ParseException pe) {
				// ignore this exception, we will try the next format
			}
		}

		// we were unable to parse the date
		throw new RuntimeException("Unable to parse the date " + dateValue);
	}

	// RFC 1123 pattern.
	public static String formatDate(Date date) {
		return formatDate(date, PATTERN_RFC1123);
	}

	public static String formatDate(Date date, String pattern) {
		if (date == null) {
			throw new IllegalArgumentException("date is null");
		}
		if (pattern == null) {
			throw new IllegalArgumentException("pattern is null");
		}

		SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.US);
		formatter.setTimeZone(GMT);
		return formatter.format(date);
	}
}
