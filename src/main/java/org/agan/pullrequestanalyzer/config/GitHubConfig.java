package org.agan.pullrequestanalyzer.config;

import org.agan.pullrequestanalyzer.service.github.GitHubOrganizationServiceV3;
import org.agan.pullrequestanalyzer.service.github.GitHubRepositoryServiceV3;
import org.agan.pullrequestanalyzer.util.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Configuration
@ConfigurationProperties("pullrequestanalyzer.github")
public class GitHubConfig {

    String host;
    String username;
    String personalAccessToken;

    @Bean
    public GitHubOrganizationServiceV3 gitHubOrganizationService(Map<String, String> gitHubAuthHeaders) {
        return new GitHubOrganizationServiceV3(new RestTemplate(), host, gitHubAuthHeaders());
    }

    @Bean
    public GitHubRepositoryServiceV3 gitHubRepositoryService() {
        return new GitHubRepositoryServiceV3(new RestTemplate(), host, gitHubAuthHeaders());
    }

    @Bean
    public Map<String, String> gitHubAuthHeaders() {
        return AuthenticationUtil.getBasicAuthHeaders(username, personalAccessToken);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonalAccessToken() {
        return personalAccessToken;
    }

    public void setPersonalAccessToken(String personalAccessToken) {
        this.personalAccessToken = personalAccessToken;
    }
}
