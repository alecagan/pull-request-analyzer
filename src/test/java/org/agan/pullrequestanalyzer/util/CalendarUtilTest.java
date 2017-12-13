package org.agan.pullrequestanalyzer.util;

import org.agan.pullrequestanalyzer.domain.TimePeriod;
import org.agan.pullrequestanalyzer.util.CalendarUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class CalendarUtilTest {

    @Test
    public void getWeekPeriodContainingDateTest()
    {
        TimePeriod period = CalendarUtil.getWeekPeriodContainingDate(new Date(1512362966000L));

        Assert.assertNotNull(period);
        Date startDate = period.getStartDate();
        Date endDate = period.getEndDate();
        Assert.assertNotNull(startDate);
        Assert.assertNotNull(endDate);

        Assert.assertEquals(new Date(1512259200000L), startDate);
        Assert.assertEquals(new Date(1512863999999L), endDate);

        period = CalendarUtil.getWeekPeriodContainingDate(new Date(1481662086000L));
        Assert.assertEquals(new Date(1481414400000L), period.getStartDate());
        Assert.assertEquals(new Date(1482019199999L), period.getEndDate());
    }

    @Test
    public void getTimePeriodsSinceTest()
    {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(new Date(1481662086000L));

        List<TimePeriod> weeksInYear = CalendarUtil.getTimePeriodsSince(calendar.getTime());

        Assert.assertNotNull(weeksInYear);

        TimePeriod earliestPeriod = weeksInYear.get(weeksInYear.size() - 1);
        Assert.assertEquals(new Date(1481414400000L), earliestPeriod.getStartDate());
        Assert.assertEquals(new Date(1482019199999L), earliestPeriod.getEndDate());
    }
}
