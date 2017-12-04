package org.agan.pullrequestanalyzer.service.github;

import org.agan.pullrequestanalyzer.dto.github.PullRequestDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GitHubRepositoryServiceV3Test {
    private GitHubRepositoryServiceV3 repositoryService;

    @Before
    public void setup()
    {
        Map<String, String> headersMap = new HashMap<>();
        repositoryService = new GitHubRepositoryServiceV3(new RestTemplate(),"api.github.com", headersMap);
    }

    @Test
    @Ignore // Ignored so that builds aren't dependant on an external service.
    public void fetchPullRequestsTest() throws Exception
    {
        final String repoName = "ramda";
        List<PullRequestDTO> pullRequests = repositoryService.fetchPullRequests("ramda", repoName, null, null, null, null, null);
        Assert.assertNotNull(pullRequests);

        //Verify some part of this DTO is populated correctly.
        PullRequestDTO firstPullRequest = pullRequests.get(0);
        Assert.assertEquals(repoName, firstPullRequest.getHead().getRepo().getName());
    }
}

