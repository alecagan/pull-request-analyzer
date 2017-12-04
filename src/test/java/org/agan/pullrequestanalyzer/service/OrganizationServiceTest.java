package org.agan.pullrequestanalyzer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.agan.pullrequestanalyzer.domain.Organization;
import org.agan.pullrequestanalyzer.domain.TimePeriod;
import org.agan.pullrequestanalyzer.dto.github.PullRequestDTO;
import org.agan.pullrequestanalyzer.dto.github.RepositoryDTO;
import org.agan.pullrequestanalyzer.service.github.GitHubOrganizationServiceV3;
import org.agan.pullrequestanalyzer.service.github.GitHubRepositoryServiceV3;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class OrganizationServiceTest {
    private OrganizationService pullAllService;

    // Used in testing.
    private final String orgName = "ramda";

    @Before
    public void setup() throws Exception{
        // Mock the organization and repository services
        GitHubOrganizationServiceV3 organizationService = Mockito.mock(GitHubOrganizationServiceV3.class);
        GitHubRepositoryServiceV3 repositoryServiceV3 = Mockito.mock(GitHubRepositoryServiceV3.class);

        RepositoryDTO[] mockRepositories = deserializeJsonFile("/repositories.json", RepositoryDTO[].class);
        when(organizationService.getRepositoriesForOrganization(orgName, 1)).thenReturn(Arrays.asList(mockRepositories));

        when(repositoryServiceV3.fetchPullRequests(anyString(), anyString(), any(), anyString(), anyString(), any(), any(), anyInt())).thenAnswer(
                new Answer<List<PullRequestDTO>>() {
                    List<String> reposAccessed = new ArrayList();

                    @Override
                    public List<PullRequestDTO> answer(InvocationOnMock invocationOnMock) throws Throwable {
                        Object[] args = invocationOnMock.getArguments();
                        String repoName = (String) args[1];

                        // Return empty list the second time, so that the org service recognizes that it's fetched all PRs.
                        if(reposAccessed.contains(repoName)) {
                            return new ArrayList<>();
                        }
                        else {
                            reposAccessed.add(repoName);
                        }

                        return Arrays.asList(deserializeJsonFile("/ramda_" + repoName + ".json", PullRequestDTO[].class));
                    }
                }
        );

        pullAllService = new OrganizationService(organizationService, repositoryServiceV3);
    }

    @Test
    public void testGetOrganization()
    {
        Organization org = pullAllService.getOrganization(orgName);

        Assert.assertNotNull(org);
        Assert.assertEquals(11, org.getRepositories().size());

        //TODO: move this to a test specific to the Organization class
        Date startDate = new Date(1510549594000L);
        Date endDate = new Date(1512277613000L);

        Assert.assertEquals(16, org.getMergedPullRequests(new TimePeriod(startDate, endDate)));
    }

    @Test
    public void testGetPullRequests()
    {
        List<PullRequestDTO> pullRequests = pullAllService.getAllPullRequests(orgName, "ramda");
        Assert.assertEquals(30, pullRequests.size());

        pullRequests = pullAllService.getAllPullRequests(orgName, "ramda-highland");
        Assert.assertTrue(pullRequests.isEmpty());
    }

    private <T> T deserializeJsonFile(String fileName, Class<T> clazz) throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        String json = IOUtils.toString(this.getClass().getResourceAsStream(fileName), "UTF-8");
        return mapper.readValue(json, clazz);
    }
}