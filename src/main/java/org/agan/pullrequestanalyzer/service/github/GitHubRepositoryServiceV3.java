package org.agan.pullrequestanalyzer.service.github;

import org.agan.pullrequestanalyzer.dto.github.PullRequestDTO;
import org.agan.pullrequestanalyzer.service.LoggingApiService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Class used to make requests to GitHub's Pull Request API.
 *
 * GitHub documentation: https://developer.github.com/v3/pulls
 */
public class GitHubRepositoryServiceV3 extends LoggingApiService
{
    private final String gitHubHost;
    private final HttpHeaders defaultHeaders;

    /**
     * Constructor
     * @param restTemplate - used to make REST calls to GitHub
     * @param githubHost - hostname, e.g. "api.github.com"
     * @param defaultHeaders - any headers that are required on all requests
     */
    public GitHubRepositoryServiceV3(RestTemplate restTemplate, String githubHost, Map<String, String> defaultHeaders)
    {
        super(restTemplate);

        this.gitHubHost = githubHost;
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
     * Fetches a list of pull requests for the given owner and repo.
     *
     * @param owner - username of owner of repo
     * @param repo - repository name
     * @param state - filter by state. Default: all
     * @param head - filter by head user/branch: format: [user]:[ref-name]
     * @param base - filter by base branch name
     * @param sort - result sorting. Default: created
     * @param direction - result sorting order. Default: if sorted by created, descending. Otherwise, ascending.
     * @return
     *
     * @see https://developer.github.com/v3/pulls/#list-pull-requests
     */
    public List<PullRequestDTO> fetchPullRequests(String owner, String repo, PullRequestState state, String head, String base, ResultsSortType sort, ResultsSortDirection direction) {
        UriComponentsBuilder urlBuilder = UriComponentsBuilder.newInstance();
        urlBuilder.scheme("https").host(gitHubHost).pathSegment("repos", owner, repo, "pulls");

        if(state != null) {
            urlBuilder.queryParam("state", state.apiText);
        }
        if(head != null) {
            urlBuilder.queryParam("head", head);
        }
        if(base != null) {
            urlBuilder.queryParam("base", base);
        }
        if(sort != null) {
            urlBuilder.queryParam("sort", sort.apiText);
        }
        if(direction != null) {
            urlBuilder.queryParam("direction", direction.apiText);
        }

        PullRequestDTO[] pullRequests = get(urlBuilder.build().toUri(), defaultHeaders, PullRequestDTO[].class);

        if(pullRequests != null)
        {
            return Arrays.asList(pullRequests);
        }

        return null;
    }

    public enum PullRequestState
    {
        OPEN ("open"),
        CLOSED("closed"),
        ALL("all");

        public String apiText;

        PullRequestState(String apiText)
        {
            this.apiText = apiText;
        }
    }

    public enum ResultsSortType
    {
        CREATED_DATE ("created"),
        UPDATED_DATE("updated"),
        POPULARITY ("popularity"),
        LONG_RUNNING("long-running");

        public String apiText;

        ResultsSortType(String apiText)
        {
            this.apiText = apiText;
        }
    }

    public enum ResultsSortDirection
    {
        ASCENDING("asc"),
        DESCENDING("desc");

        public String apiText;

        ResultsSortDirection(String apiText)
        {
            this.apiText = apiText;
        }

    }

}
