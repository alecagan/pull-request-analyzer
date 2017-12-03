package org.agan.pullrequestanalyzer.service.github;

import org.agan.pullrequestanalyzer.dto.github.RepositoryDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class GitHubOrganizationServiceV3Test {

    private GitHubOrganizationServiceV3 organizationService;

    @Before
    public void setup()
    {
        organizationService = new GitHubOrganizationServiceV3(new RestTemplate(), "api.github.com", null);
    }

    @Test
    @Ignore
    public void getReposForOrganizationTest() {
        final String orgName = "ramda";
        List<RepositoryDTO> repositories = organizationService.getRepositoriesForOrganization(orgName);

        Assert.assertNotNull(repositories);

        Assert.assertEquals(11, repositories.size());
        for(RepositoryDTO repo : repositories) {
            Assert.assertNotNull(repo.getName());
            Assert.assertEquals(orgName,repo.getOwner().getLogin());
        }
    }
}
