package com.duberton.github;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import com.duberton.github.client.GithubClient.GithubClientGeneric;
import com.duberton.github.client.UserClient;
import com.duberton.github.client.UserClient.Repository;
import com.duberton.github.factory.RepositoryFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import feign.FeignException;
import java.util.List;
import org.apache.http.HttpStatus;
import org.junit.Rule;
import org.junit.Test;

public class GithubIntegrationTest {

  @Rule
  public WireMockRule wireMockRule = new WireMockRule(
      WireMockConfiguration.wireMockConfig().dynamicPort());

  @Test
  public void givenAValidSetOfParametersInTheRequest_ThenItShouldPerformTheRequestSuccessfully()
      throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    List<Repository> repositoryList = RepositoryFactory.createRepositoryList();
    wireMockRule.stubFor(
        WireMock.get(WireMock.urlEqualTo("/users/duberton/repos"))
            .withHeader("Accept", equalTo("application/json"))
            .withHeader("Content-Type", equalTo("application/json"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withStatus(200)
                .withBody(objectMapper.writeValueAsString(repositoryList))));

    UserClient userClient = new GithubClientGeneric()
        .buildClient(UserClient.class, String.format("http://localhost:%s", wireMockRule.port()));

    List<Repository> reposFromDuberton = userClient.reposFromUser("duberton");
    assertThat(reposFromDuberton.size(), is(1));
    Repository repository = reposFromDuberton.get(0);
    assertThat(repository.getName(), is("feign"));
    assertThat(repository.getFork(), is(Boolean.TRUE));
    assertThat(repository.getFullName(), is("io.github.openfeign/feign"));
    assertThat(repository.getCreatedAt(), is(notNullValue()));

    verify(1, getRequestedFor(urlEqualTo("/users/duberton/repos"))
        .withHeader("Accept", equalTo("application/json"))
        .withHeader("Content-Type", equalTo("application/json")));
  }

  @Test(expected = FeignException.class)
  public void givenAMalformedDataInTheRequest_ThenItShouldPerformTheRequestAndReturnAnError()
      throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    List<Repository> repositoryList = RepositoryFactory.createRepositoryList();
    wireMockRule.stubFor(
        WireMock.get(WireMock.urlEqualTo("/users/duberton/repos"))
            .withHeader("Accept", equalTo("application/json"))
            .withHeader("Content-Type", equalTo("application/json"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withStatus(HttpStatus.SC_OK)
                .withBody(objectMapper.writeValueAsString(repositoryList))
                .withFault(Fault.MALFORMED_RESPONSE_CHUNK)));

    UserClient userClient = new GithubClientGeneric()
        .buildClient(UserClient.class, String.format("http://localhost:%s", wireMockRule.port()));

    List<Repository> reposFromDuberton = userClient.reposFromUser("duberton");
    assertThat(reposFromDuberton.size(), is(1));
  }
}
