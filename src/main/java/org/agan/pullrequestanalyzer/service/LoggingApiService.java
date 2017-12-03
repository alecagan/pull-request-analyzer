package org.agan.pullrequestanalyzer.service;

import com.fasterxml.jackson.core.JsonParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * Class for making REST requests. Wrap's Spring's RestTemplate, logging any errors.
 */
public abstract class LoggingApiService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected RestTemplate restTemplate;

    protected LoggingApiService(RestTemplate restTemplate)
    {
        this.restTemplate = restTemplate;
    }

    protected <T> T get(URI url, HttpHeaders headers, Class<T> returnType)
    {
        RequestEntity request = new RequestEntity(headers, HttpMethod.GET, url);
        try
        {
            return restTemplate.exchange(request, returnType).getBody();
        }
        catch(HttpStatusCodeException e)
        {
            logger.warn("Error making GET request to {} - status code {}. {}", url.toString(), e.getStatusCode(), e.getMessage());
        }
        catch(RestClientException e)
        {
            logger.warn("Error making GET request to {}. {}", url.toString(), e.getMessage());
        }

        return null;
    }
}
