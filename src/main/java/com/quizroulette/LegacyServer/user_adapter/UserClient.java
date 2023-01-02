package com.quizroulette.LegacyServer.user_adapter;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class UserClient {

  private final WebClient webClient;

  public Mono<User> getUserByIdAsync(final String id) {
    return webClient
        .get()
        .uri(String.join("", "/user/", id))
        .retrieve()
        .bodyToMono(User.class)
        .onErrorReturn(new User());
        //.blockOptional();
  }

  public void addUser(User user) {
    log.info("Adding a new... user {}", user.getUsername());
    Mono<ResponseEntity<Void>> responseEntityMono = webClient
        .post()
        .uri(String.join("", "/user"))
        .body(Mono.just(user), User.class)
        .retrieve()
        .toBodilessEntity();
    log.info("GEtting a retrieval {}", responseEntityMono.block().getStatusCode());
  }

  public void updateUser(User user) {
    log.info("Updating a... user {}", user.getUsername());
    Mono<ResponseEntity<Void>> responseEntityMono = webClient
        .put()
        .uri(String.join("", "/user"))
        .body(Mono.just(user), User.class)
        .retrieve()
        .toBodilessEntity();
    log.info("GEtting a retrieval {}", responseEntityMono.block().getStatusCode());
  }

}
