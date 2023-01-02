package com.quizroulette.LegacyServer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Setter
public class QuizLog {

  private String id;
  @JsonProperty("QuizId")
  private String quizId;
  @JsonProperty("QuizUserId")
  private String quizUserId; //OriginalUserId
  @JsonProperty("QuestionId")
  private Integer questionId;
  @JsonProperty("ChoiceId")
  private String choiceId;
  @JsonProperty("Score")
  private Integer score;
  @JsonProperty("TimeTaken")
  private Integer timeTaken;
  @JsonProperty("Avatar")
  private String avatar;
  @JsonProperty("OriginalUserId")
  private String originalUserId;

  public void addScore(Integer score) {
    this.score += score;
  }

  public void addTimeTaken(Integer timeTaken) {
    this.timeTaken += timeTaken;
  }
}
