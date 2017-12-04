package org.agan.pullrequestanalyzer.domain;


import org.agan.pullrequestanalyzer.dto.github.PullRequestDTO;

import java.util.List;

public class Organization {
    private String name;
    private List<Repository> repositories;

    public Organization() {

    }

    public int getMergedPullRequests(TimePeriod timePeriod)
    {
        int total = 0;
        for(Repository repo : repositories)
        {
            List<PullRequestDTO> pullRequests = repo.getMergedPullRequests(timePeriod);
            total += pullRequests.size();
        }

        return total;
    }

    public Organization(String name, List<Repository> repositories) {
        this.name = name;
        this.repositories = repositories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Repository> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<Repository> repositories) {
        this.repositories = repositories;
    }
}
