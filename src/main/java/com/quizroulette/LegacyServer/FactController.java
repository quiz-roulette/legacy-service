package com.quizroulette.LegacyServer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FactController {

  @GetMapping("/getRandomFact")
  public Fact getRandomFact() {
    return new Fact("There is a supermassive black hole at the heart of every galaxy!");
  }

  @AllArgsConstructor
  @Getter
  static class Fact {
    @JsonProperty("Text")
    private String text;
  }
}
