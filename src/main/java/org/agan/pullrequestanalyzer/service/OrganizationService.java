package org.agan.pullrequestanalyzer.service;

import org.agan.pullrequestanalyzer.domain.Organization;
import org.agan.pullrequestanalyzer.domain.Repository;
import org.agan.pullrequestanalyzer.domain.TimePeriod;
import org.agan.pullrequestanalyzer.dto.github.PullRequestDTO;
import org.agan.pullrequestanalyzer.dto.github.RepositoryDTO;
import org.agan.pullrequestanalyzer.dto.github.UserDTO;
import org.agan.pullrequestanalyzer.service.github.GitHubOrganizationServiceV3;
import org.agan.pullrequestanalyzer.service.github.GitHubRepositoryServiceV3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class designed to pull all information on an Organization
 */
public class OrganizationService {

    private final Logger log = LoggerFactory.getLogger(OrganizationService.class);
    private GitHubOrganizationServiceV3 organizationService;
    private GitHubRepositoryServiceV3 repositoryService;

    // Very primitive caching...
    private Organization cachedOrg;

    public OrganizationService(GitHubOrganizationServiceV3 organizationService, GitHubRepositoryServiceV3 repositoryService) {
        this.organizationService = organizationService;
        this.repositoryService = repositoryService;

        this.cachedOrg = null;
    }

    public Organization getOrganization(String orgName)
    {
        if(cachedOrg == null || !orgName.equals(cachedOrg.getName()))
        {
            List<Repository> orgRepos = new ArrayList<>();
            List<RepositoryDTO> orgRepositories = organizationService.getRepositoriesForOrganization(orgName, 1);
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

                // TODO: Thread this, so that each repo is fetched asynchronously.
                List<PullRequestDTO> pullRequests = getAllPullRequests(repoOwner, repoName);

                log.debug(repoName + " PRs - " + pullRequests.size());
                orgRepos.add(new Repository(repoName, pullRequests));
            }
            cachedOrg = new Organization(orgName, orgRepos);
        }

        return cachedOrg;
    }

    public List<PullRequestDTO> getAllPullRequests(String repositoryOwner, String repositoryName) {
        List<PullRequestDTO> allRequests = new ArrayList<>();
        List<PullRequestDTO> pageOfRequests;

        //TODO: It's possible that a PR could be double-counted if something gets merged while this is executing,
        //      and the last item of a page moves to the next.
        int page = 1;
        do {
           pageOfRequests = repositoryService.fetchPullRequests(repositoryOwner, repositoryName, GitHubRepositoryServiceV3.PullRequestState.ALL, null, null, null, null, page);
           allRequests.addAll(pageOfRequests);
           page++;
        }
        while(pageOfRequests.size() > 0);

        return allRequests;
    }
}