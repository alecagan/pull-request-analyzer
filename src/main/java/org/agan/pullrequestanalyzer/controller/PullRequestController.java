package org.agan.pullrequestanalyzer.controller;

import org.agan.pullrequestanalyzer.domain.Organization;
import org.agan.pullrequestanalyzer.domain.TimePeriod;
import org.agan.pullrequestanalyzer.dto.pullrequestanalyzer.PeriodMergesGrowthResponse;
import org.agan.pullrequestanalyzer.dto.pullrequestanalyzer.PeriodMergesResponse;
import org.agan.pullrequestanalyzer.service.OrganizationService;
import org.agan.pullrequestanalyzer.util.CalendarUtil;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

        // Last week
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        TimePeriod mostRecentWeek = CalendarUtil.getWeekPeriodContainingDate(calendar.getTime());

        // 2 weeks ago
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        TimePeriod previousWeek = CalendarUtil.getWeekPeriodContainingDate(calendar.getTime());

        return getGrowthOverPeriods(previousWeek, mostRecentWeek, organization);
        }

    @RequestMapping("/{org}/rollingweek")
    public PeriodMergesGrowthResponse getRollingWindowMergesForOrg(@PathVariable("org") String organizationName) {
        Organization organization = organizationService.getOrganization(organizationName);
        TimePeriod currentPeriod = CalendarUtil.getWeekPeriodEndingNow(0);
        TimePeriod previousPeriod = CalendarUtil.getWeekPeriodEndingNow(-1);

        return getGrowthOverPeriods(previousPeriod, currentPeriod, organization);
    }

    @RequestMapping("/{org}/historicalWeeks")
    public List<PeriodMergesResponse> countAllMergesByWeek(@PathVariable("org") String organizationName, @RequestParam(name="since", defaultValue = "0") long msSinceEpoch) {
        Organization organization = organizationService.getOrganization(organizationName);

        Date earliestDate = new Date(msSinceEpoch);

        List<PeriodMergesResponse> responseList = new ArrayList<>();
        List<TimePeriod> periods = CalendarUtil.getTimePeriodsSince(earliestDate);
        for(TimePeriod week : periods) {
            int mergedPullRequests = organization.getMergedPullRequests(week);
            responseList.add(new PeriodMergesResponse(week, mergedPullRequests));
        }

        return responseList;
    }

    private PeriodMergesGrowthResponse getGrowthOverPeriods(TimePeriod baselinePeriod, TimePeriod currentPeriod, Organization organization) {
        int currentMerges = organization.getMergedPullRequests(currentPeriod);
        PeriodMergesResponse currentMergesResponse = new PeriodMergesResponse(currentPeriod, currentMerges);

        int previousMerges = organization.getMergedPullRequests(baselinePeriod);
        PeriodMergesResponse previousMergesResponse = new PeriodMergesResponse(baselinePeriod, previousMerges);

        return new PeriodMergesGrowthResponse(previousMergesResponse, currentMergesResponse);
    }
}