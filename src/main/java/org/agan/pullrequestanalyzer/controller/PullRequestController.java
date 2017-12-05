package org.agan.pullrequestanalyzer.controller;

import org.agan.pullrequestanalyzer.domain.Organization;
import org.agan.pullrequestanalyzer.domain.TimePeriod;
import org.agan.pullrequestanalyzer.dto.pullrequestanalyzer.PeriodMergesGrowthResponse;
import org.agan.pullrequestanalyzer.dto.pullrequestanalyzer.PeriodMergesResponse;
import org.agan.pullrequestanalyzer.service.OrganizationService;
import org.agan.pullrequestanalyzer.util.CalendarUtil;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.TimeZone;

@RestController
@RequestMapping("/pullrequests")
public class PullRequestController {
    private OrganizationService organizationService;

    public PullRequestController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @RequestMapping("/{org}/weekoverweek")
    public PeriodMergesGrowthResponse getWeekOverWeekMergesForOrg(@PathVariable("org") String organizationName) {

        Organization organization = organizationService.getOrganization(organizationName);

        // "Current" period
        PeriodMergesResponse currentMerges = getOrganizationMergesInWeek(-1, organization);

        //"Previous" period
        PeriodMergesResponse previousMerges = getOrganizationMergesInWeek(-2, organization);

        double currentMergedPRs = currentMerges.getPullRequestsMerged();
        double previousMergedPRs = previousMerges.getPullRequestsMerged();

        return new PeriodMergesGrowthResponse(previousMerges, currentMerges);
        }

    @RequestMapping("/{org}/rollingwindow/{requestPeriod}")
    public PeriodMergesGrowthResponse getRollingWindowMergesForOrg(@PathVariable("org") String organization, @PathVariable("requestPeriod") RequestPeriod requestPeriod) {
        return null;
    }

    private PeriodMergesResponse getOrganizationMergesInWeek(int numWeeksFromNow, Organization organization)
    {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.add(Calendar.WEEK_OF_YEAR, numWeeksFromNow);
        TimePeriod period = CalendarUtil.getWeekPeriodContainingDate(calendar.getTime());

        int currentMergedPRs = organization.getMergedPullRequests(period);
        return new PeriodMergesResponse(period, currentMergedPRs);
    }

    public enum RequestPeriod{
        WEEK
    }
}