package org.agan.pullrequestanalyzer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.agan.pullrequestanalyzer.domain.Organization;
import org.agan.pullrequestanalyzer.domain.TimePeriod;
import org.agan.pullrequestanalyzer.dto.github.PullRequestDTO;
import org.agan.pullrequestanalyzer.dto.github.RepositoryDTO;
import org.agan.pullrequestanalyzer.service.github.GitHubOrganizationServiceV3;
import org.agan.pullrequestanalyzer.service.github.GitHubRepositoryServiceV3;
import org.agan.pullrequestanalyzer.service.mock.GitHubRepositoryServiceMockFactory;
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
        GitHubRepositoryServiceV3 repositoryServiceV3 = GitHubRepositoryServiceMockFactory.createJsonBackedMock();

        RepositoryDTO[] mockRepositories = deserializeJsonFile("/repositories.json", RepositoryDTO[].class);
        when(organizationService.getRepositoriesForOrganization(orgName, 1)).thenReturn(Arrays.asList(mockRepositories));


        pullAllService = new OrganizationService(organizationService, repositoryServiceV3, 1);
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

    private <T> T deserializeJsonFile(String fileName, Class<T> clazz) throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        String json = IOUtils.toString(this.getClass().getResourceAsStream(fileName), "UTF-8");
        return mapper.readValue(json, clazz);
    }
}