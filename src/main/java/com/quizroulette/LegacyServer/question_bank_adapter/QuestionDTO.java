package com.quizroulette.LegacyServer.question_bank_adapter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class QuestionDTO {

  @JsonProperty("QuestionId")
  private int QuestionId;
  @JsonProperty("CategoryName")
  private String CategoryName;
  @JsonProperty("Text")
  private String Text;
  @JsonProperty("ImageUrl")
  private String ImageUrl;
}
