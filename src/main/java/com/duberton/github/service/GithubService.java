package com.duberton.github.service;

import com.duberton.github.client.UserClient;
import com.duberton.github.client.UserClient.Repository;
import com.duberton.github.model.mapper.RepositoryMapper;
import com.duberton.github.model.vo.RepositoryVo;
import com.duberton.github.model.vo.UserVo;
import com.google.inject.Inject;
import java.util.List;

public class GithubService {

  private UserClient userClient;

  @Inject
  public GithubService(UserClient userClient) {
    this.userClient = userClient;
  }

  public UserVo getReposFromUser(final String username) {
    List<Repository> repositories = userClient.reposFromUser(username);
    List<RepositoryVo> repositoryVos = RepositoryMapper.mapDomainListToVo(repositories);
    return UserVo.newBuilder().withRepositories(repositoryVos).build();
  }

}
