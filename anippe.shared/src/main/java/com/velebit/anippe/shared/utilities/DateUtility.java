package com.velebit.anippe.shared.utilities;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateUtility {
	public static LocalDate now() {
		return LocalDate.now();
	}

	public static LocalDate add(long amount, ChronoUnit unit) {
		return now().plus(amount, unit);
	}

	public static Date startOfDay() {
		return localDateTimeToDate(now().atStartOfDay());
	}

	public static Date endOfDay() {
		return localDateTimeToDate(now().atTime(LocalTime.MAX));
	}

	public static LocalDateTime getNearestHourQuarter(LocalDateTime datetime) {

		int minutes = datetime.getMinute();
		int mod = minutes % 15;
		LocalDateTime newDatetime;
		if (mod < 8) {
			newDatetime = datetime.minusMinutes(mod);
		} else {
			newDatetime = datetime.plusMinutes(15 - mod);
		}

		newDatetime = newDatetime.truncatedTo(ChronoUnit.MINUTES);

		return newDatetime;
	}

	public static LocalDate dateToLocalDate(Date date) {
		return new java.sql.Date(date.getTime()).toLocalDate();
	}

	public static LocalDate dateToLocalDateNullable(Date date) {
		return date == null ? null : new java.sql.Date(date.getTime()).toLocalDate();
	}

	public static LocalTime dateToLocalTime(Date date) {
		return new java.sql.Time(date.getTime()).toLocalTime();
	}

	public static LocalTime dateToLocalTimeNullable(Date date) {
		return date == null ? null : new java.sql.Time(date.getTime()).toLocalTime();
	}

	public static LocalDateTime dateToLocalDateTime(Date date) {
		return new Timestamp(date.getTime()).toLocalDateTime();
	}

	public static LocalDateTime dateToLocalDateTimeNullable(Date date) {
		return date == null ? null : new Timestamp(date.getTime()).toLocalDateTime();
	}

	public static LocalDate timestampToLocalDate(Timestamp timestamp) {
		return timestamp.toLocalDateTime().toLocalDate();
	}

	public static LocalDate timestampToLocalDateNullable(Timestamp timestamp) {
		return timestamp == null ? null : timestamp.toLocalDateTime().toLocalDate();
	}

	public static LocalDateTime timestampToLocalDateTime(Timestamp timestamp) {
		return timestamp.toLocalDateTime();
	}

	public static LocalDateTime timestampToLocalDateTimeNullable(Timestamp timestamp) {
		return timestamp == null ? null : timestamp.toLocalDateTime();
	}

	public static Date localDateToDate(LocalDate localDate) {
		return java.sql.Date.valueOf(localDate);
	}

	public static Date localDateToDateNullable(LocalDate localDate) {
		return localDate == null ? null : java.sql.Date.valueOf(localDate);
	}

	public static Date localTimeToDate(LocalTime localTime) {
		return java.sql.Time.valueOf(localTime);
	}

	public static Date localTimeToDateNullable(LocalTime localTime) {
		return localTime == null ? null : java.sql.Time.valueOf(localTime);
	}

	public static Date localDateTimeToDate(LocalDateTime localDateTime) {
		return Timestamp.valueOf(localDateTime);
	}

	public static Date localDateTimeToDateNullable(LocalDateTime localDateTime) {
		return localDateTime == null ? null : Timestamp.valueOf(localDateTime);
	}

}
