package org.agan.pullrequestanalyzer.service.concurrency;

import org.agan.pullrequestanalyzer.domain.Repository;
import org.agan.pullrequestanalyzer.service.github.GitHubRepositoryServiceV3;
import org.agan.pullrequestanalyzer.service.mock.GitHubRepositoryServiceMockFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RepositoryCallableTest {

    private GitHubRepositoryServiceV3 repositoryServiceV3;

    @Before
    public void setup() {
        repositoryServiceV3 = GitHubRepositoryServiceMockFactory.createJsonBackedMock();
    }

    @Test
    public void callTest() throws Exception {
        final String organization = "ramda";

        RepositoryCallable repositoryCallable = new RepositoryCallable(organization, "ramda", repositoryServiceV3);
        Repository repository = repositoryCallable.call();
        Assert.assertEquals(30, repository.getPullRequests().size());

        repositoryCallable = new RepositoryCallable(organization, "ramda-highland", repositoryServiceV3);
        repository = repositoryCallable.call();
        Assert.assertTrue(repository.getPullRequests().isEmpty());

    }
}
