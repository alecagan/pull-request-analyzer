package org.agan.pullrequestanalyzer.config;

import org.agan.pullrequestanalyzer.controller.PullRequestController;
import org.agan.pullrequestanalyzer.service.OrganizationService;
import org.agan.pullrequestanalyzer.service.github.GitHubOrganizationServiceV3;
import org.agan.pullrequestanalyzer.service.github.GitHubRepositoryServiceV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PullRequestAnalyzerConfig {


    @Bean
    public PullRequestController pullRequestController(OrganizationService organizationService) {
        return new PullRequestController(organizationService);
    }

    @Bean
    @Autowired // Autowired from GitHubConfig.java
    public OrganizationService organizationService(GitHubOrganizationServiceV3 gitHubOrganizationServiceV3, GitHubRepositoryServiceV3 gitHubRepositoryServiceV3) {
        return new OrganizationService(gitHubOrganizationServiceV3, gitHubRepositoryServiceV3);
    }
}
