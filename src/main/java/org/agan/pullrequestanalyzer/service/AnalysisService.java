package org.agan.pullrequestanalyzer.service;

import org.agan.pullrequestanalyzer.dto.github.PullRequestDTO;
import org.agan.pullrequestanalyzer.dto.github.RepositoryDTO;
import org.agan.pullrequestanalyzer.dto.github.UserDTO;
import org.agan.pullrequestanalyzer.service.github.GitHubOrganizationServiceV3;
import org.agan.pullrequestanalyzer.service.github.GitHubRepositoryServiceV3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AnalysisService {

    private final Logger log = LoggerFactory.getLogger(AnalysisService.class);
    private GitHubOrganizationServiceV3 organizationService;
    private GitHubRepositoryServiceV3 repositoryService;

    public AnalysisService(GitHubOrganizationServiceV3 organizationService, GitHubRepositoryServiceV3 repositoryService) {
        this.organizationService = organizationService;
        this.repositoryService = repositoryService;
    }

    public List<PullRequestDTO> findMergedPullRequestsInTimeRangeAccrossOrg(String orgName, Date startDate, Date endDate) {
        List<PullRequestDTO> mergedPullRequests = new ArrayList<>();

        // Fetch repositories within org
        List<RepositoryDTO> orgRepositories = organizationService.getRepositoriesForOrganization(orgName);
        for(RepositoryDTO repo : orgRepositories)
        {
            String repoName = repo.getName();
            if(repoName == null)
            {
                log.error("Returned repository has null name for org {}. Repo id: {}. This repo's merge requests will not be returned.", orgName, repo.getId());
                continue;
            }

            // In case there's a way for the repo to have a different name as the owner than the orgName
            String repoOwner = null;
            UserDTO ownerObj = repo.getOwner();
            if(ownerObj != null)
            {
                repoOwner = ownerObj.getLogin();
            }
            if(repoOwner == null)
            {
                log.error("Owner of returned repository {} has null name for org {}. Repo id: {}.  This repo's merge requests will not be returned.", repoName, orgName, repo.getId());
                continue;
            }

            List<PullRequestDTO> mergedPRs = findMergedPullRequestsInTimeRange(repoOwner, repoName, startDate, endDate);

            log.debug(repoName + " - " + mergedPRs.size());
            mergedPullRequests.addAll(mergedPRs);
        }

        return mergedPullRequests;
    }

    public List<PullRequestDTO> findMergedPullRequestsInTimeRange(String repositoryOwner, String repositoryName, Date startDate, Date endDate) {
        List<PullRequestDTO> allRequests = repositoryService.fetchPullRequests(repositoryOwner, repositoryName, GitHubRepositoryServiceV3.PullRequestState.CLOSED, null, null, GitHubRepositoryServiceV3.ResultsSortType.UPDATED_DATE, GitHubRepositoryServiceV3.ResultsSortDirection.DESCENDING);

        // TODO: Update this for pagination.
        return allRequests.stream().filter(i -> i.getMergedAt() != null && i.getMergedAt().after(startDate) && i.getMergedAt().before(endDate)).collect(Collectors.toList());
    }
}