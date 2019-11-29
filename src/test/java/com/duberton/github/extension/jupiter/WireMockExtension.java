package com.duberton.github.extension.jupiter;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import java.lang.reflect.Parameter;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class WireMockExtension implements BeforeAllCallback, AfterAllCallback, ParameterResolver {

  private static final Namespace NAMESPACE = Namespace.create(WireMockExtension.class);
  private static final String SERVER_KEY = "SERVER";


  @Override
  public void afterAll(ExtensionContext context) throws Exception {
    WireMockServer wireMockServer = context.getStore(NAMESPACE).get(SERVER_KEY, WireMockServer.class);
    wireMockServer.stop();
    context.getStore(NAMESPACE).remove(SERVER_KEY);
  }

  @Override
  public void beforeAll(ExtensionContext context) throws Exception {
    WireMockServer wireMockServer = new WireMockServer(wireMockConfig().dynamicPort());
    wireMockServer.start();
    WireMock.configureFor("localhost", wireMockServer.port());
    context.getStore(NAMESPACE).put(SERVER_KEY, wireMockServer);
  }

  @Override
  public boolean supportsParameter(ParameterContext parameterContext,
      ExtensionContext extensionContext) throws ParameterResolutionException {
    Parameter parameter = parameterContext.getParameter();
    Class<?> type = parameter.getType();
    return WireMockServer.class.isAssignableFrom(type);
  }

  @Override
  public Object resolveParameter(ParameterContext parameterContext,
      ExtensionContext extensionContext) throws ParameterResolutionException {
    return extensionContext.getStore(NAMESPACE).get(SERVER_KEY);
  }
}
