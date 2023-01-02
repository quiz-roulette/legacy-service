package com.quizroulette.LegacyServer.question_bank_adapter;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionBankResponse {

  private QuestionBank data;

  @Getter
  @Setter
  public static class QuestionBank {

    private List<Question> questions;

    @Getter
    @Setter
    public static class Question {

      private int questionId;
      private String text;
      private List<Choice> choice;
      private String description;
      private String imageUrl;
      private String category;

      @Getter
      @Setter
      public static class Choice {

        private String text;
        private int choiceId;
        @JsonProperty("isCorrect")
        private boolean isCorrect;
      }
    }
  }
}
