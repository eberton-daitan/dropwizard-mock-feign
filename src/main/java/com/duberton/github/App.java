package com.duberton.github;

import com.duberton.github.module.AppModule;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class App extends Application<AppConfiguration> {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  public static void main(String[] args) throws Exception {
    new App().run(args);
  }

  @Override
  public void initialize(Bootstrap<AppConfiguration> bootstrap) {
    GuiceBundle<Configuration> guiceBundle = GuiceBundle.builder()
        .modules(new AppModule())
        .build();
    bootstrap.addBundle(guiceBundle);
  }

  @Override
  public String getName() {
    return "github app";
  }

  public void run(AppConfiguration configuration, Environment environment) throws Exception {
    environment.jersey().setUrlPattern("/api/*");
    environment.healthChecks().register("app", new AppHealthCheck());
  }
}
