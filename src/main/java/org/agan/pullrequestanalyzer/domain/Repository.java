package org.agan.pullrequestanalyzer.domain;

import org.agan.pullrequestanalyzer.dto.github.PullRequestDTO;

import java.util.List;
import java.util.stream.Collectors;

public class Repository {
    private String name;
    private List<PullRequestDTO> pullRequests; // TODO: Make a domain class for pull requests, if necessary.

    public Repository() {

    }

    public Repository(String name, List<PullRequestDTO> pullRequests) {
        this.name = name;
        this.pullRequests = pullRequests;
    }

    public List<PullRequestDTO> getMergedPullRequests(TimePeriod period)
    {
        return pullRequests.stream().filter(i -> i.getMergedAt() != null && i.getMergedAt().after(period.getStartDate()) && i.getMergedAt().before(period.getEndDate())).collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PullRequestDTO> getPullRequests() {
        return pullRequests;
    }

    public void setPullRequests(List<PullRequestDTO> pullRequests) {
        this.pullRequests = pullRequests;
    }
}
