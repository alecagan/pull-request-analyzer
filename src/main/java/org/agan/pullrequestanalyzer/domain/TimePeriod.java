package org.agan.pullrequestanalyzer.domain;

import java.util.Date;

public class TimePeriod {
    private Date startDate;
    private Date endDate;

    public TimePeriod() {

    }

    public TimePeriod(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
