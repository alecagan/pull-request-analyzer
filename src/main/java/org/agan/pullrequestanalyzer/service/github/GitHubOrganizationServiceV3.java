package org.agan.pullrequestanalyzer.service.github;

import org.agan.pullrequestanalyzer.dto.github.RepositoryDTO;
import org.agan.pullrequestanalyzer.service.LoggingApiService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GitHubOrganizationServiceV3 extends LoggingApiService {

    private final String gitHubHost;
    private final HttpHeaders defaultHeaders;

    public GitHubOrganizationServiceV3(RestTemplate restTemplate, String gitHubHost, Map<String, String> defaultHeaders)
    {
        super(restTemplate);

        this.gitHubHost = gitHubHost;
        this.defaultHeaders = new HttpHeaders();
        if(defaultHeaders != null)
        {
            for(Map.Entry<String, String> mapEntry : defaultHeaders.entrySet())
            {
                this.defaultHeaders.set(mapEntry.getKey(), mapEntry.getValue());
            }
        }
    }

    /**
     * Fetch repositories for an organization
     * @param organizationName
     * @param page - page of results to retrieve. 1-indexed.
     * @return List of RepositoryDTOs
     *
     * GitHub Documentation: https://developer.github.com/v3/repos/#list-organization-repositories
     */
    public List<RepositoryDTO> getRepositoriesForOrganization(String organizationName, int page) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance().scheme("https").host(gitHubHost)
                .pathSegment("orgs", organizationName, "repos").queryParam("page", page).queryParam("per_page", 100);

        RepositoryDTO[] repositories = get(uriBuilder.build().toUri(), defaultHeaders, RepositoryDTO[].class);
        if(repositories != null)
        {
            return Arrays.asList(repositories);
        }

        return null;
    }
}
