package org.agan.pullrequestanalyzer.util;

import org.agan.pullrequestanalyzer.domain.TimePeriod;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CalendarUtil {
    public static TimePeriod getWeekPeriodContainingDate(Date containedDate)
    {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(containedDate);

        //Sunday at midnight
        calendar.add(Calendar.DAY_OF_WEEK, -(calendar.get(Calendar.DAY_OF_WEEK)-1));
        calendar.set(Calendar.HOUR,  0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date startDate = calendar.getTime();

        calendar.add(Calendar.DAY_OF_WEEK, 7);
        calendar.add(Calendar.MILLISECOND, -1);
        Date endDate = calendar.getTime();

        return new TimePeriod(startDate, endDate);
    }
}
