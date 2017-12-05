package org.agan.pullrequestanalyzer.service;

import org.agan.pullrequestanalyzer.domain.Organization;
import org.agan.pullrequestanalyzer.domain.Repository;
import org.agan.pullrequestanalyzer.dto.github.RepositoryDTO;
import org.agan.pullrequestanalyzer.dto.github.UserDTO;
import org.agan.pullrequestanalyzer.service.concurrency.RepositoryCallable;
import org.agan.pullrequestanalyzer.service.github.GitHubOrganizationServiceV3;
import org.agan.pullrequestanalyzer.service.github.GitHubRepositoryServiceV3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Class designed to pull all information on an Organization
 */
public class OrganizationService {

    private final Logger log = LoggerFactory.getLogger(OrganizationService.class);
    private GitHubOrganizationServiceV3 organizationService;
    private GitHubRepositoryServiceV3 repositoryService;
    private ExecutorService executor;


    // Very primitive caching...
    private Organization cachedOrg;

    public OrganizationService(GitHubOrganizationServiceV3 organizationService, GitHubRepositoryServiceV3 repositoryService, int numThreads) {
        this.organizationService = organizationService;
        this.repositoryService = repositoryService;
        this.executor = Executors.newFixedThreadPool(numThreads);

        this.cachedOrg = null;
    }

    public Organization getOrganization(String orgName)
    {
        if(cachedOrg == null || !orgName.equals(cachedOrg.getName()))
        {
            List<Repository> orgRepos = new ArrayList<>();
            List<RepositoryDTO> orgRepositories = organizationService.getRepositoriesForOrganization(orgName, 1);

            List<Future<Repository>> futureRepos = new ArrayList<>();

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

                RepositoryCallable repositoryCallable = new RepositoryCallable(repoOwner, repoName, repositoryService);
                futureRepos.add(executor.submit(repositoryCallable));

            }

            // Wait for all repos to be retrieved.
            for(Future<Repository> future : futureRepos) {
                try {
                    orgRepos.add(future.get());
                }
                catch(Exception e) {
                    log.error("Asynchronous fetching of pull requests ran into an error!", e);
                }
            }

            executor.shutdown();

            cachedOrg = new Organization(orgName, orgRepos);
        }

        return cachedOrg;
    }
}