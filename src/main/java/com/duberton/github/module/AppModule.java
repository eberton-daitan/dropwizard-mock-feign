package com.duberton.github.module;

import com.duberton.github.AppConfiguration;
import com.duberton.github.client.GithubClient.GithubClientGeneric;
import com.duberton.github.client.UserClient;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import ru.vyarus.dropwizard.guice.module.support.ConfigurationAwareModule;

public class AppModule implements Module, ConfigurationAwareModule<AppConfiguration> {

  private AppConfiguration appConfiguration;

  @Override
  public void configure(Binder binder) {
  }

  @Override
  public void setConfiguration(AppConfiguration appConfiguration) {
    this.appConfiguration = appConfiguration;
  }

  @Provides
  @Singleton
  public UserClient githubClient() {
    return new GithubClientGeneric()
        .buildClient(UserClient.class, appConfiguration.getGithubConfig().getUrl());
  }
}
