package com.quizroulette.LegacyServer.quiz_log_adapater;

import com.quizroulette.LegacyServer.entity.QuizLog;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class QuizLogClient {

  @Value("${quiz-roulette.quiz-log-service-url}")
  private String BASE_URL;

  public List<QuizLog> getQuizLogs() {
    log.info("Getting Logs");
    Flux<QuizLog> quizLogFlux = getWebClient().get().uri("/quizlogs")
        .retrieve()
        .bodyToFlux(QuizLog.class);
    log.info("Getting Logs 2 {}", quizLogFlux);
    return quizLogFlux.collectList().block();
  }

  public Flux<QuizLog> getQuizLogs(String quizId) {
    log.info("Getting Logs");
    return getWebClient().get().uri("/quizlogs/"+quizId+"/summary")
        .retrieve()
        .bodyToFlux(QuizLog.class);
  }

  public void addQuizLog(QuizLog quizLog) {
    log.info("Adding a new... QuizLog {}", quizLog.getQuizId());
    Mono<ResponseEntity<Void>> responseEntityMono = getWebClient()
        .post()
        .uri(String.join("", "/quizlogs"))
        .body(Mono.just(quizLog), QuizLog.class)
        .retrieve()
        .toBodilessEntity();
    log.info("GEtting a retrieval {}", responseEntityMono.block().getStatusCode());
  }

  private WebClient getWebClient() {
    log.info("Getting WebClient for url" + BASE_URL);
    return WebClient.builder()
        .baseUrl(BASE_URL)
        .build();
  }

}
