package com.tibco.as.simulator.provider;

import java.util.Calendar;
import java.util.Random;

import javax.xml.datatype.XMLGregorianCalendar;

import com.tibco.as.simulator.IValueProvider;
import com.tibco.as.simulator.RandomDateTime;

public class DateTimeProvider implements IValueProvider {

	private Random random;
	private RandomDateTime field;

	public DateTimeProvider(Random random, RandomDateTime field) {
		this.random = random;
		this.field = field;
	}

	@Override
	public Calendar getValue() {
		Calendar cal;
		if (field.getYear() == null) {
			cal = Calendar.getInstance();
			if (field.getStart() != null) {
				cal.setTime(getDate(field.getStart()));
			}
			Calendar end = Calendar.getInstance();
			if (field.getEnd() != null) {
				end.setTime(getDate(field.getEnd()));
			}
			long offset = cal.getTimeInMillis();
			long diff = end.getTimeInMillis() - offset + 1;
			cal.setTimeInMillis(offset + (long) (random.nextDouble() * diff));
		} else {
			cal = Calendar.getInstance();
			cal.set(field.getYear(), field.getMonth() - 1, field.getDay(),
					field.getHour(), field.getMinute(), field.getSecond());
			cal.set(Calendar.MILLISECOND, field.getMillisecond());
		}
		if (Boolean.TRUE.equals(field.isDateOnly())) {
			truncate(cal);
		}
		return cal;
	}

	private Calendar truncate(Calendar calendar) {
		calendar.setLenient(false);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}

	private java.util.Date getDate(XMLGregorianCalendar date) {
		return date.toGregorianCalendar().getTime();
	}

}
