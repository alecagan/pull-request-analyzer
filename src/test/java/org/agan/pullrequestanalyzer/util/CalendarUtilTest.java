package org.agan.pullrequestanalyzer.util;

import org.agan.pullrequestanalyzer.domain.TimePeriod;
import org.agan.pullrequestanalyzer.util.CalendarUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

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
    }
}
