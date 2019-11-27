package com.duberton.github.factory;

import com.duberton.github.client.UserClient.Repository;
import java.util.List;

public final class RepositoryFactory {

  public static List<Repository> createRepositoryList() {
    Repository repository = new Repository();
    repository.setName("feign");
    return List.of(repository);
  }

}
