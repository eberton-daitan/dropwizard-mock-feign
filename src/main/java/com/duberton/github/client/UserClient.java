package com.duberton.github.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import java.util.List;

@Headers({
    "Content-Type: application/json",
    "Accept: application/json"
})
public interface UserClient {

  @RequestLine("GET /users/{username}/repos")
  List<Repository> reposFromUser(@Param("username") String username);

  class Repository {

    @JsonProperty("name")
    String name;
    @JsonProperty("full_name")
    String fullName;
    @JsonProperty("fork")
    Boolean fork;
    @JsonProperty("created_at")
    String createdAt;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getFullName() {
      return fullName;
    }

    public void setFullName(String fullName) {
      this.fullName = fullName;
    }

    public Boolean getFork() {
      return fork;
    }

    public void setFork(Boolean fork) {
      this.fork = fork;
    }

    public String getCreatedAt() {
      return createdAt;
    }

    public void setCreatedAt(String createdAt) {
      this.createdAt = createdAt;
    }
  }

}
