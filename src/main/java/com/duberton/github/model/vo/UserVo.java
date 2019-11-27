package com.duberton.github.model.vo;

import java.util.List;

public class UserVo {

  private final List<RepositoryVo> repositories;

  private UserVo(Builder builder) {
    repositories = builder.repositories;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public List<RepositoryVo> getRepositories() {
    return repositories;
  }

  public static final class Builder {

    private List<RepositoryVo> repositories;

    private Builder() {
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
