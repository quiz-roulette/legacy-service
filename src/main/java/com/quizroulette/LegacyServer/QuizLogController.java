package com.quizroulette.LegacyServer;

import com.quizroulette.LegacyServer.entity.QuizLog;
import com.quizroulette.LegacyServer.quiz_log_adapater.QuizLogClient;
import com.quizroulette.LegacyServer.user_adapter.User;
import com.quizroulette.LegacyServer.user_adapter.UserClient;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequiredArgsConstructor
public class QuizLogController {

  List<QuizLog> inMemoryQuizLogs = new ArrayList<>();

  private final UserClient userClient;
  private final QuizLogClient quizLogClient;

  @GetMapping("/quizlog")
  public Flux<QuizLog> getQuizLogsByQuiz(@RequestParam("QuizId") String quizId) {
    log.info("Existing logs {}", inMemoryQuizLogs);

    return quizLogClient.getQuizLogs(quizId)
        .map(this::updateQuizUserId);
  }

  @PostMapping("/quizlog")
  public String addQuizLog(@RequestBody QuizLog quizLog) {
    quizLogClient.addQuizLog(quizLog);
    return "ok";
  }

  @GetMapping("/getQuizLogSummationForUserByQuiz")
  public List<Object> getQuizLogSummation(@RequestParam("quizid") String quizId,
      @RequestParam("userid") String userId) {
    return List.of();
  }

  private QuizLog updateQuizUserId(QuizLog quizLog) {
    String old = quizLog.getQuizUserId();
    User block = userClient.getUserByIdAsync(old).share().block();
    if (block != null) {
      quizLog.setAvatar(block.getAvatarUrl());
    }
    if (quizLog.getQuizUserId().contains(quizLog.getQuizId() + "_")) {
      quizLog.setQuizUserId(quizLog.getQuizUserId().split("_")[1]);
    }
    if (quizLog.getAvatar() == null) {
      old = quizLog.getQuizUserId();
      block = userClient.getUserByIdAsync(old).share().block();
      if (block != null) {
        quizLog.setAvatar(block.getAvatarUrl());
      }
    }
    log.info("Updating Quiz Log ID: {} {}", quizLog.getQuizId(), quizLog);
    return quizLog;
  }
}
