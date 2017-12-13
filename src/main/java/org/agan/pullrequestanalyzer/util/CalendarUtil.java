package org.agan.pullrequestanalyzer.util;

import org.agan.pullrequestanalyzer.domain.TimePeriod;

import java.util.*;

public class CalendarUtil {
    public static TimePeriod getWeekPeriodContainingDate(Date containedDate)
    {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(containedDate);

        //Sunday at midnight
        calendar.add(Calendar.DAY_OF_WEEK, -(calendar.get(Calendar.DAY_OF_WEEK)-1));
        calendar.set(Calendar.HOUR_OF_DAY,  0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date startDate = calendar.getTime();

        calendar.add(Calendar.DAY_OF_WEEK, 7);
        calendar.add(Calendar.MILLISECOND, -1);
        Date endDate = calendar.getTime();

        return new TimePeriod(startDate, endDate);
    }

    public static TimePeriod getWeekPeriodEndingNow(int offset)
    {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.add(Calendar.WEEK_OF_YEAR, (offset));

        Date endDate = calendar.getTime();
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        calendar.add(Calendar.MILLISECOND, 1);
        Date startDate = calendar.getTime();

        return new TimePeriod(startDate, endDate);
    }

    public static List<TimePeriod> getTimePeriodsSince(Date earliestDate) {
        // Determine merges for each week
        List<TimePeriod> weeks = new ArrayList<>();

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        Date dateInWeek = cal.getTime();
        while(dateInWeek.after(earliestDate)) {
            weeks.add(CalendarUtil.getWeekPeriodContainingDate(dateInWeek));

            cal.setTime(dateInWeek);
            cal.add(Calendar.WEEK_OF_YEAR, -1);
            dateInWeek = cal.getTime();
        }

        return weeks;
    }
}
