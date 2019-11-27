package com.duberton.github;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;

import com.duberton.github.client.GithubClient.GithubClientGeneric;
import com.duberton.github.client.UserClient;
import com.duberton.github.client.UserClient.Repository;
import com.duberton.github.factory.RepositoryFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import java.util.List;
import org.apache.http.HttpStatus;
import org.junit.Rule;
import org.junit.Test;

public class GithubIntegrationTest {

  @Rule
  public WireMockRule wireMockRule = new WireMockRule(
      WireMockConfiguration.wireMockConfig().dynamicPort());

  @Test
  public void givenAValidUser_ThenItShouldPerformTheRequestSuccessfully()
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
                .withBody(objectMapper.writeValueAsString(repositoryList)))
    );
    UserClient userClient = new GithubClientGeneric()
        .buildClient(UserClient.class, String.format("http://localhost:%s", wireMockRule.port()));

    List<Repository> duberton = userClient.reposFromUser("duberton");
  }
}
