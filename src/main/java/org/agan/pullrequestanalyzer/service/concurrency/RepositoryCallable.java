package org.agan.pullrequestanalyzer.service.concurrency;

import org.agan.pullrequestanalyzer.domain.Repository;
import org.agan.pullrequestanalyzer.dto.github.PullRequestDTO;
import org.agan.pullrequestanalyzer.service.github.GitHubRepositoryServiceV3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Fetches all pull requests for a given repository. Can be run asynchronously.
 */
public class RepositoryCallable implements Callable<Repository> {

    private final Logger log = LoggerFactory.getLogger(RepositoryCallable.class);
    private String repoOwner;
    private String repoName;
    private GitHubRepositoryServiceV3 repositoryService;

    public RepositoryCallable(String repoOwner, String repoName, GitHubRepositoryServiceV3 repositoryService)
    {
        this.repoOwner = repoOwner;
        this.repoName = repoName;
        this.repositoryService = repositoryService;
    }

    @Override
    public Repository call() throws Exception {
        List<PullRequestDTO> allRequests = new ArrayList<>();
        List<PullRequestDTO> pageOfRequests;

        //TODO: It's possible that a PR could be double-counted if something gets merged while this is executing,
        //      and the last item of a page moves to the next.
        int page = 1;
        do {
            pageOfRequests = repositoryService.fetchPullRequests(repoOwner, repoName, GitHubRepositoryServiceV3.PullRequestState.ALL, null, null, null, null, page);
            allRequests.addAll(pageOfRequests);
            page++;
        }
        while(pageOfRequests.size() > 0);

        log.debug(repoName + " PRs - " + allRequests.size());

        return new Repository(repoName, allRequests);
    }
}