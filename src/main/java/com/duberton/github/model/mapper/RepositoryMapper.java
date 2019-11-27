package com.duberton.github.model.mapper;

import static java.util.stream.Collectors.toList;

import com.duberton.github.client.UserClient.Repository;
import com.duberton.github.model.vo.RepositoryVo;
import java.util.List;

public final class RepositoryMapper {

  private RepositoryMapper() {
  }

  public static List<RepositoryVo> mapDomainListToVo(List<Repository> repositories) {
    return repositories.stream()
        .map(RepositoryMapper::mapDomainToVo)
        .collect(toList());
  }

  public static RepositoryVo mapDomainToVo(Repository repository) {
    return RepositoryVo.newBuilder()
        .withName(repository.getName())
        .withFullName(repository.getFullName())
        .withFork(repository.getFork())
        .withCreatedAt(repository.getCreatedAt())
        .build();
  }
}
