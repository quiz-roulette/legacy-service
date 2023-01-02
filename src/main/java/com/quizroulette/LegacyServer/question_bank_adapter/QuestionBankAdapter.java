package com.quizroulette.LegacyServer.question_bank_adapter;

import com.quizroulette.LegacyServer.question_bank_adapter.QuestionBankResponse.QuestionBank.Question;
import com.quizroulette.LegacyServer.question_bank_adapter.QuestionBankResponse.QuestionBank.Question.Choice;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class QuestionBankAdapter {

  public static final String QUERY = "{\"query\":\"query getAllQuestions {\\n  questions {\\n    questionId\\n    text\\n    imageUrl\\n    category\\n    choice {\\n      choiceId\\n      text\\n      isCorrect\\n    }\\n  }\\n}\"}";

  private QuestionBankClient questionBankClient;
  private CategoryQuizMappingProperties categoryQuizMappingProperties;

  public List<QuestionDTO> getQuestions(String category) {

    if (categoryQuizMappingProperties.getQuizCategoryMapping().get(category) != null) {
      category = categoryQuizMappingProperties.getQuizCategoryMapping().get(category);
    }

    QuestionBankResponse questionBankResponse = questionBankClient.getQuestions();

    String finalCategory = category;
    return questionBankResponse.getData().getQuestions().stream()
        .filter(question -> question.getCategory().equals(finalCategory))
        .map(this::map)
        .collect(Collectors.toList());
  }

  public List<ChoiceDTO> getChoices() {
    QuestionBankResponse questionBankResponse = questionBankClient.getQuestions();

    List<Question> questions = questionBankResponse.getData().getQuestions();

    List<ChoiceDTO> choiceDTOS = new ArrayList<>();
    for (Question question : questions) {
      List<Choice> choices = question.getChoice();
      for (Choice choice : choices) {
        ChoiceDTO choiceDTO = map(choice, question.getQuestionId());
        choiceDTOS.add(choiceDTO);
      }
    }

    return choiceDTOS;
  }

  public List<CorrectChoiceDTO> getCorrectChoices() {
    QuestionBankResponse questionBankResponse = questionBankClient.getQuestions();

    List<Question> questions = questionBankResponse.getData().getQuestions();

    List<CorrectChoiceDTO> correctChoiceDTOS = new ArrayList<>();
    for (Question question : questions) {
      List<Choice> choices = question.getChoice();
      for (Choice choice : choices) {
        if (choice.isCorrect()) {
          correctChoiceDTOS.add(CorrectChoiceDTO.builder().ChoiceId(choice.getChoiceId())
              .QuestionId(question.getQuestionId()).build());
        }
      }
    }
    return correctChoiceDTOS;
  }

  private QuestionDTO map(Question question) {
    return QuestionDTO.builder()
        .QuestionId(question.getQuestionId())
        .CategoryName(question.getCategory())
        .Text(question.getText())
        .ImageUrl(question.getImageUrl())
        .build();
  }

  private ChoiceDTO map(Choice choice, int questionId) {
    return ChoiceDTO.builder()
        .ChoiceId(choice.getChoiceId())
        .Text(choice.getText())
        .QuestionId(questionId)
        .build();
  }

  private List<ChoiceDTO> mapChoice(Question question) {
    return question.getChoice().stream()
        .map(choice -> map(choice, question.getQuestionId()))
        .collect(Collectors.toList());
  }

  private Map<String, String> headers() {
    return Map.of(
        "Content-Type", "application/json",
        "Accept", "application/json",
        "Connection", "keep-alive"
    );
  }
}
