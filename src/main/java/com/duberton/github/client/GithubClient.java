package com.duberton.github.client;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

public interface GithubClient {

  <T> T buildClient(Class<T> klazz, String url);

  class GithubClientGeneric implements GithubClient {

    @Override
    public <T> T buildClient(Class<T> klazz, String url) {
      return Feign.builder()
          .encoder(new JacksonEncoder())
          .decoder(new JacksonDecoder())
          .target(klazz, url);
    }
  }

}
