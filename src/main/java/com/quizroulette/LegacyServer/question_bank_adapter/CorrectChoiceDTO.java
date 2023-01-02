package com.quizroulette.LegacyServer.question_bank_adapter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CorrectChoiceDTO {

  @JsonProperty("ChoiceId")
  private int ChoiceId;
  @JsonProperty("QuestionId")
  private int QuestionId;
}
