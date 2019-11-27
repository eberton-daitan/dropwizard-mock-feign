package com.duberton.github.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RepositoryVo {

  @JsonProperty("name")
  private final String name;
  @JsonProperty("full_name")
  private final String fullName;
  @JsonProperty("is_a_fork")
  private final Boolean fork;
  @JsonProperty("created_at")
  private final String createdAt;

  private RepositoryVo(Builder builder) {
    name = builder.name;
    fullName = builder.fullName;
    fork = builder.fork;
    createdAt = builder.createdAt;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public String getName() {
    return name;
  }

  public String getFullName() {
    return fullName;
  }

  public Boolean getFork() {
    return fork;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public static final class Builder {

    private String name;
    private String fullName;
    private Boolean fork;
    private String createdAt;

    private Builder() {
    }

    public Builder withName(String name) {
      this.name = name;
      return this;
    }

    public Builder withFullName(String fullName) {
      this.fullName = fullName;
      return this;
    }

    public Builder withFork(Boolean fork) {
      this.fork = fork;
      return this;
    }

    public Builder withCreatedAt(String createdAt) {
      this.createdAt = createdAt;
      return this;
    }

    public RepositoryVo build() {
      return new RepositoryVo(this);
    }
  }
}
