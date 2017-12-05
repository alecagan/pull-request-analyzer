package org.agan.pullrequestanalyzer.dto.pullrequestanalyzer;

import org.agan.pullrequestanalyzer.domain.TimePeriod;

public class PeriodMergesResponse {
    private long periodStart;
    private long periodEnd;
    private int pullRequestsMerged;

    public PeriodMergesResponse() {

    }

    public PeriodMergesResponse(TimePeriod period, int pullRequestsMerged) {
        this(period.getStartDate().getTime(), period.getEndDate().getTime(), pullRequestsMerged);
    }

    public PeriodMergesResponse(long periodStart, long periodEnd, int pullRequestsMerged) {
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.pullRequestsMerged = pullRequestsMerged;
    }

    public long getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(long periodStart) {
        this.periodStart = periodStart;
    }

    public long getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(long periodEnd) {
        this.periodEnd = periodEnd;
    }

    public int getPullRequestsMerged() {
        return pullRequestsMerged;
    }

    public void setPullRequestsMerged(int pullRequestsMerged) {
        this.pullRequestsMerged = pullRequestsMerged;
    }
}
