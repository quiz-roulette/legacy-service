package com.quizroulette.LegacyServer.configuration;

import java.time.Duration;
import reactor.netty.http.client.HttpClient;

public class HttpClientHelper {

  public static HttpClient buildDefaultHttpClient() {
    return HttpClient.create().responseTimeout(Duration.ofMillis(500)); // 500 -> timeout in millis
  }
}
