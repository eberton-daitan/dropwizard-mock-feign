package com.duberton.github.factory;

import com.duberton.github.client.UserClient.Repository;
import java.time.LocalDateTime;
import java.util.List;

public final class RepositoryFactory {

  public static List<Repository> createRepositoryList() {
    Repository repository = new Repository();
    repository.setName("feign");
    repository.setFork(Boolean.TRUE);
    repository.setCreatedAt(LocalDateTime.now().toString());
    repository.setFullName("io.github.openfeign/feign");
    return List.of(repository);
  }

}
