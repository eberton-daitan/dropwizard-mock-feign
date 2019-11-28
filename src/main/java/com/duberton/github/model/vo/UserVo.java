package com.duberton.github.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class UserVo {

  @JsonProperty("username")
  private final String username;
  @JsonProperty("repositories")
  private final List<RepositoryVo> repositories;

  private UserVo(Builder builder) {
    username = builder.username;
    repositories = builder.repositories;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public String getUsername() {
    return username;
  }

  public List<RepositoryVo> getRepositories() {
    return repositories;
  }

  public static final class Builder {

    private String username;
    private List<RepositoryVo> repositories;

    private Builder() {
    }

    public Builder withUsername(String username) {
      this.username = username;
      return this;
    }

    public Builder withRepositories(List<RepositoryVo> repositories) {
      this.repositories = repositories;
      return this;
    }

    public UserVo build() {
      return new UserVo(this);
    }
  }
}
