package com.duberton.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

public class AppConfiguration extends Configuration {

  @JsonProperty("github")
  private GithubConfig githubConfig;

  public GithubConfig getGithubConfig() {
    return githubConfig;
  }

  public static class GithubConfig {

    @JsonProperty("url")
    private String url;

    public String getUrl() {
      return url;
    }

  }
}
