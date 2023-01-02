package com.quizroulette.LegacyServer.question_bank_adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QuestionBankClient {

  @Value("${quiz-roulette.question-bank-url}")
  public String url;

  public QuestionBankResponse getQuestions() {
    OkHttpClient client = new OkHttpClient().newBuilder()
        .build();
    MediaType mediaType = MediaType.parse("application/json");
    RequestBody body = RequestBody.create(
        "{\"query\":\"query getAllQuestions {\\n  questions {\\n    questionId\\n    text\\n    imageUrl\\n    category\\n    choice {\\n      choiceId\\n      text\\n      isCorrect\\n    }\\n  }\\n}\",\"variables\":{}}",
        mediaType);
    Request request = new Request.Builder()
        .url(url)
        .method("POST", body)
        .addHeader("Content-Type", "application/json")
        .build();
    try {
      Response response = client.newCall(request).execute();
      ResponseBody body1 = response.body();
      ObjectMapper objectMapper = new ObjectMapper();
      QuestionBankResponse questionBankResponse = objectMapper
          .readValue(body1.string(), QuestionBankResponse.class);
      return questionBankResponse;
    } catch (IOException e) {
      log.error(e.getLocalizedMessage());
      throw new RuntimeException("Unable to connect to the server");
    }
  }
}
