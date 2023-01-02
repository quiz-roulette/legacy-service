package com.quizroulette.LegacyServer.question_bank_adapter;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "quiz-roulette")
public class CategoryQuizMappingProperties {

  private Map<String, String> quizCategoryMapping;
}
