package pl.incidents.utils;

import java.time.LocalDateTime;

public class CreateDate {

	public LocalDateTime createDateFromRequest(String dateInString, int hour, int min) {

		int day = Integer.parseInt(dateInString.substring(0, 2));
		int month = Integer.parseInt(dateInString.substring(3, 5));
		int year = Integer.parseInt(dateInString.substring(6, 10));
		LocalDateTime date = LocalDateTime.of(year, month, day, hour, min);
		return date;

	}

}
