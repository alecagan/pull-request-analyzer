package org.agan.pullrequestanalyzer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.agan.pullrequestanalyzer.dto.github.PullRequestDTO;
import org.agan.pullrequestanalyzer.dto.github.RepositoryDTO;
import org.agan.pullrequestanalyzer.service.github.GitHubOrganizationServiceV3;
import org.agan.pullrequestanalyzer.service.github.GitHubRepositoryServiceV3;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

public class AnalysisServiceTest {
    private AnalysisService analysisService;

    // Used in testing.
    private final Date startDate = new Date(1510549594000L);
    private final Date endDate = new Date(1512277613000L);
    private final String orgName = "ramda";


    @Before
    public void setup() throws Exception{
        // Mock the organization and repository services
        GitHubOrganizationServiceV3 organizationService = Mockito.mock(GitHubOrganizationServiceV3.class);
        GitHubRepositoryServiceV3 repositoryServiceV3 = Mockito.mock(GitHubRepositoryServiceV3.class);

        RepositoryDTO[] mockRepositories = deserializeJsonFile("/repositories.json", RepositoryDTO[].class);
        when(organizationService.getRepositoriesForOrganization(orgName)).thenReturn(Arrays.asList(mockRepositories));

        List<PullRequestDTO> cdnjsPRs = Arrays.asList(deserializeJsonFile("/ramda_cdnjs.json", PullRequestDTO[].class));
        List<PullRequestDTO> eslintPRs = Arrays.asList(deserializeJsonFile("/ramda_eslint-plugin-ramda.json", PullRequestDTO[].class));
        List<PullRequestDTO> jsdelivrPRs = Arrays.asList(deserializeJsonFile("/ramda_jsdelivr.json", PullRequestDTO[].class));
        List<PullRequestDTO> githubIoPRs = Arrays.asList(deserializeJsonFile("/ramda_ramda.github.io.json", PullRequestDTO[].class));
        List<PullRequestDTO> ramdaPRs = Arrays.asList(deserializeJsonFile("/ramda_ramda.json", PullRequestDTO[].class));
        List<PullRequestDTO> ramdaFantasyPRs = Arrays.asList(deserializeJsonFile("/ramda_ramda-fantasy.json", PullRequestDTO[].class));
        List<PullRequestDTO> highlandPRs = Arrays.asList(deserializeJsonFile("/ramda_ramda-highland.json", PullRequestDTO[].class));
        List<PullRequestDTO> lensPRs = Arrays.asList(deserializeJsonFile("/ramda_ramda-lens.json", PullRequestDTO[].class));
        List<PullRequestDTO> logicPRs = Arrays.asList(deserializeJsonFile("/ramda_ramda-logic.json", PullRequestDTO[].class));
        List<PullRequestDTO> ramdangularPRs = Arrays.asList(deserializeJsonFile("/ramda_ramdangular.json", PullRequestDTO[].class));
        List<PullRequestDTO> replPRs = Arrays.asList(deserializeJsonFile("/ramda_repl.json", PullRequestDTO[].class));

        when(repositoryServiceV3.fetchPullRequests("ramda", "cdnjs", GitHubRepositoryServiceV3.PullRequestState.CLOSED, null, null, GitHubRepositoryServiceV3.ResultsSortType.UPDATED_DATE, GitHubRepositoryServiceV3.ResultsSortDirection.DESCENDING))
                .thenReturn(cdnjsPRs);
        when(repositoryServiceV3.fetchPullRequests("ramda", "eslint-plugin-ramda", GitHubRepositoryServiceV3.PullRequestState.CLOSED, null, null, GitHubRepositoryServiceV3.ResultsSortType.UPDATED_DATE, GitHubRepositoryServiceV3.ResultsSortDirection.DESCENDING))
                .thenReturn(eslintPRs);
        when(repositoryServiceV3.fetchPullRequests("ramda", "jsdelivr", GitHubRepositoryServiceV3.PullRequestState.CLOSED, null, null, GitHubRepositoryServiceV3.ResultsSortType.UPDATED_DATE, GitHubRepositoryServiceV3.ResultsSortDirection.DESCENDING))
                .thenReturn(jsdelivrPRs);
        when(repositoryServiceV3.fetchPullRequests("ramda", "ramda.github.io", GitHubRepositoryServiceV3.PullRequestState.CLOSED, null, null, GitHubRepositoryServiceV3.ResultsSortType.UPDATED_DATE, GitHubRepositoryServiceV3.ResultsSortDirection.DESCENDING))
                .thenReturn(githubIoPRs);
        when(repositoryServiceV3.fetchPullRequests("ramda", "ramda", GitHubRepositoryServiceV3.PullRequestState.CLOSED, null, null, GitHubRepositoryServiceV3.ResultsSortType.UPDATED_DATE, GitHubRepositoryServiceV3.ResultsSortDirection.DESCENDING))
                .thenReturn(ramdaPRs);
        when(repositoryServiceV3.fetchPullRequests("ramda", "ramda-fantasy", GitHubRepositoryServiceV3.PullRequestState.CLOSED, null, null, GitHubRepositoryServiceV3.ResultsSortType.UPDATED_DATE, GitHubRepositoryServiceV3.ResultsSortDirection.DESCENDING))
                .thenReturn(ramdaFantasyPRs);
        when(repositoryServiceV3.fetchPullRequests("ramda", "ramda-highland", GitHubRepositoryServiceV3.PullRequestState.CLOSED, null, null, GitHubRepositoryServiceV3.ResultsSortType.UPDATED_DATE, GitHubRepositoryServiceV3.ResultsSortDirection.DESCENDING))
                .thenReturn(highlandPRs);
        when(repositoryServiceV3.fetchPullRequests("ramda", "ramda-lens", GitHubRepositoryServiceV3.PullRequestState.CLOSED, null, null, GitHubRepositoryServiceV3.ResultsSortType.UPDATED_DATE, GitHubRepositoryServiceV3.ResultsSortDirection.DESCENDING))
                .thenReturn(lensPRs);
        when(repositoryServiceV3.fetchPullRequests("ramda", "ramda-logic", GitHubRepositoryServiceV3.PullRequestState.CLOSED, null, null, GitHubRepositoryServiceV3.ResultsSortType.UPDATED_DATE, GitHubRepositoryServiceV3.ResultsSortDirection.DESCENDING))
                .thenReturn(logicPRs);
        when(repositoryServiceV3.fetchPullRequests("ramda", "ramdangular", GitHubRepositoryServiceV3.PullRequestState.CLOSED, null, null, GitHubRepositoryServiceV3.ResultsSortType.UPDATED_DATE, GitHubRepositoryServiceV3.ResultsSortDirection.DESCENDING))
                .thenReturn(ramdangularPRs);
        when(repositoryServiceV3.fetchPullRequests("ramda", "ramdangular", GitHubRepositoryServiceV3.PullRequestState.CLOSED, null, null, GitHubRepositoryServiceV3.ResultsSortType.UPDATED_DATE, GitHubRepositoryServiceV3.ResultsSortDirection.DESCENDING))
                .thenReturn(ramdangularPRs);
        when(repositoryServiceV3.fetchPullRequests("ramda", "repl", GitHubRepositoryServiceV3.PullRequestState.CLOSED, null, null, GitHubRepositoryServiceV3.ResultsSortType.UPDATED_DATE, GitHubRepositoryServiceV3.ResultsSortDirection.DESCENDING))
                .thenReturn(replPRs);

        analysisService = new AnalysisService(organizationService, repositoryServiceV3);
    }

    @Test
    public void findClosedPullRequestsInTimeRangeTest() {
        List<PullRequestDTO> requestsInWindow = analysisService.findMergedPullRequestsInTimeRange("ramda", "ramda", startDate, endDate);

        Assert.assertNotNull(requestsInWindow);
        Assert.assertEquals(4, requestsInWindow.size());
    }

    @Test
    public void findPullRequestsForOrg() {
        List<PullRequestDTO> requestsInWindow = analysisService.findMergedPullRequestsInTimeRangeAccrossOrg(orgName, startDate, endDate);

        Assert.assertNotNull(requestsInWindow);
        Assert.assertEquals(16, requestsInWindow.size());
    }

    private <T> T deserializeJsonFile(String fileName, Class<T> clazz) throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        String json = IOUtils.toString(this.getClass().getResourceAsStream(fileName), "UTF-8");
        return mapper.readValue(json, clazz);
    }
}