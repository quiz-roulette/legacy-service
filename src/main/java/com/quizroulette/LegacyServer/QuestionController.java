package com.quizroulette.LegacyServer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.quizroulette.LegacyServer.question_bank_adapter.ChoiceDTO;
import com.quizroulette.LegacyServer.question_bank_adapter.CorrectChoiceDTO;
import com.quizroulette.LegacyServer.question_bank_adapter.QuestionBankAdapter;
import com.quizroulette.LegacyServer.question_bank_adapter.QuestionDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class QuestionController {

  private QuestionBankAdapter questionBankAdapter;

  @GetMapping("/getQuestionCountForQuiz")
  public List<QuestionCount> getQuestionCount(@RequestParam("QuizId") String quizId) {
    return List.of(new QuestionCount(questionBankAdapter.getQuestions(quizId).size()));
  }

  @GetMapping("/question")
  public List<QuestionDTO> getQuestions(@RequestParam("userid") String userId,
      @RequestParam("quizid") String quizId) {
    return questionBankAdapter.getQuestions(quizId);
  }

  @GetMapping("/choice")
  public List<ChoiceDTO> getChoices() {
    return questionBankAdapter.getChoices();
  }

  @GetMapping("/correctchoice")
  public List<CorrectChoiceDTO> getCorrectChoice() {
    return questionBankAdapter.getCorrectChoices();
  }

  @Getter
  @AllArgsConstructor
  static class QuestionCount {

    @JsonProperty("Count")
    Integer count;
  }


}
