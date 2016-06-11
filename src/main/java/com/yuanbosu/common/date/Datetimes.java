package com.yuanbosu.common.date;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Datetimes {

	public static Date parse(String input, String pattern) {
		input = StringUtils.trimToNull(input);
		if (input == null)
			return null;
		try {
			DateTimeFormatter format = DateTimeFormat.forPattern(pattern);
			return DateTime.parse(input, format).toDate();
		} catch (Exception ex) {
		}
		return null;
	}

	public static Date parse(String input) {
		return parse(input, "yyyy-MM-dd");
	}

	public static DateTime startOfDay(Date date) {
		DateTime dt = new DateTime(date);
		dt = dt.hourOfDay().withMinimumValue();
		dt = dt.minuteOfHour().withMinimumValue();
		dt = dt.secondOfMinute().withMinimumValue();
		dt = dt.millisOfSecond().withMinimumValue();

		return dt;
	}

	public static DateTime startOfWeek(Date date) {
		DateTime dt = startOfDay(date);
		return dt.dayOfWeek().withMinimumValue();
	}

	public static DateTime startOfMonth(Date date) {
		DateTime dt = startOfWeek(date);
		return dt.dayOfMonth().withMinimumValue();
	}

	public static DateTime startOfYear(Date date) {
		DateTime dt = startOfMonth(date);
		return dt.dayOfYear().withMinimumValue();
	}

	public static DateTime endOfDay(Date date) {
		DateTime dt = new DateTime(date);

		dt = dt.hourOfDay().withMaximumValue();
		dt = dt.minuteOfHour().withMaximumValue();
		dt = dt.secondOfMinute().withMaximumValue();
		dt = dt.millisOfSecond().withMaximumValue();

		return dt;
	}

	public static DateTime endOfWeek(Date date) {
		DateTime dt = endOfDay(date);
		return dt.dayOfWeek().withMaximumValue();
	}

	public static DateTime endOfMonth(Date date) {
		DateTime dt = endOfWeek(date);
		return dt.dayOfMonth().withMaximumValue();
	}

	public static DateTime endOfYear(Date date) {
		DateTime dt = endOfMonth(date);
		return dt.dayOfYear().withMaximumValue();
	}

	public static Date idNumber2Date(String input) {
		input = StringUtils.trimToEmpty(input);

		String dt = StringUtils.substring(input, 6, 14);
		return parse(dt, "yyyyMMdd");
	}

	public static String now() {
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyyMMddHHmmssSSS");
		return DateTime.now().toString(format);
	}

	public static void main(String[] args) {
		System.out.println(idNumber2Date("rwrwrwr"));
	}
}
