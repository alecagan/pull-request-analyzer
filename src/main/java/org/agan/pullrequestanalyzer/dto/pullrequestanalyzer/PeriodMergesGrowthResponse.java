package org.agan.pullrequestanalyzer.dto.pullrequestanalyzer;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.NumberFormat;

public class PeriodMergesGrowthResponse {
    private PeriodMergesResponse previousPeriod;
    private PeriodMergesResponse currentPeriod;
    private double periodOverPeriodGrowth;

    public PeriodMergesGrowthResponse() {

    }

    public PeriodMergesGrowthResponse(PeriodMergesResponse previousPeriod, PeriodMergesResponse currentPeriod) {
        this.previousPeriod = previousPeriod;
        this.currentPeriod = currentPeriod;

        double currentMerges = currentPeriod.getPullRequestsMerged();
        double previousMerges = previousPeriod.getPullRequestsMerged();

        this.periodOverPeriodGrowth = (currentMerges - previousMerges) / previousMerges;
    }

    public PeriodMergesResponse getPreviousPeriod() {
        return previousPeriod;
    }

    public void setPreviousPeriod(PeriodMergesResponse previousPeriod) {
        this.previousPeriod = previousPeriod;
    }

    public PeriodMergesResponse getCurrentPeriod() {
        return currentPeriod;
    }

    public void setCurrentPeriod(PeriodMergesResponse currentPeriod) {
        this.currentPeriod = currentPeriod;
    }

    public double getPeriodOverPeriodGrowth() {
        return periodOverPeriodGrowth;
    }

    public void setPeriodOverPeriodGrowth(double periodOverPeriodGrowth) {
        this.periodOverPeriodGrowth = periodOverPeriodGrowth;
    }

    @JsonProperty("periodOverPeriodGrowth")
    public String getPeriodOverPeriodGrowthPercentage()
    {
        if(currentPeriod.getPullRequestsMerged() == 0)
        {
            return "Infinity! 0 merged a week ago.";
        }

        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        return percentFormat.format(periodOverPeriodGrowth);
    }
}
