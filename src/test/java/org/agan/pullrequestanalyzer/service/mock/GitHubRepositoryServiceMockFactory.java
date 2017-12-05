package org.agan.pullrequestanalyzer.service.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.agan.pullrequestanalyzer.dto.github.PullRequestDTO;
import org.agan.pullrequestanalyzer.service.github.GitHubRepositoryServiceV3;
import org.apache.commons.io.IOUtils;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class GitHubRepositoryServiceMockFactory {

    public static GitHubRepositoryServiceV3 createJsonBackedMock()
    {
        GitHubRepositoryServiceV3 repositoryServiceV3 = Mockito.mock(GitHubRepositoryServiceV3.class);
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


                    private <T> T deserializeJsonFile(String fileName, Class<T> clazz) throws Exception
                    {
                        ObjectMapper mapper = new ObjectMapper();
                        String json = IOUtils.toString(this.getClass().getResourceAsStream(fileName), "UTF-8");
                        return mapper.readValue(json, clazz);
                    }
                }
        );

        return repositoryServiceV3;
    }
}
