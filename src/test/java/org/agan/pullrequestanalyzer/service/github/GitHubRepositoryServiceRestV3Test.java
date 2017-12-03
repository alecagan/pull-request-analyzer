package org.agan.pullrequestanalyzer.service.github;

import org.agan.pullrequestanalyzer.dto.github.PullRequestDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GitHubRepositoryServiceRestV3Test {
    private GitHubRepositoryServiceRestV3 pullRequestService;

    @Before
    public void setup()
    {
        Map<String, String> headersMap = new HashMap<>();
        pullRequestService = new GitHubRepositoryServiceRestV3(new RestTemplate(),"api.github.com", headersMap);
    }

    //@Ignore
    @Test
    public void fetchPullRequestsTest() throws Exception
    {
        final String repoName = "ramda";
        List<PullRequestDTO> pullRequests = pullRequestService.fetchPullRequests("ramda", repoName, null, null, null, null, null);
        Assert.assertNotNull(pullRequests);

        //Verify some part of this DTO is populated correctly.
        PullRequestDTO firstPullRequest = pullRequests.get(0);
        Assert.assertEquals(repoName, firstPullRequest.getHead().getRepo().getName());
    }
}

