package com.quizroulette.LegacyServer.question_bank_adapter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import lombok.Data;
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
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper
                    .readValue(getRequest("{\"query\":\"query getAllQuestions {\\n  questions {\\n    questionId\\n    text\\n    imageUrl\\n    category\\n    choice {\\n      choiceId\\n      text\\n      isCorrect\\n    }\\n  }\\n}\",\"variables\":{}}")
                            .body()
                            .string(), QuestionBankResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CategoriesResponse getCategories() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper
                    .readValue(getRequest("{\"query\":\"query Query {\\n  getCategories\\n}\"}").body().string(), CategoriesResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addQuestion(){

    }

    private Response getRequest(String requestBody) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(requestBody
                ,
                mediaType);
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            return client.newCall(request).execute();
        } catch (IOException e) {

//            log.error(e.getLocalizedMessage());
            throw new RuntimeException("Unable to connect to the server");
        }
    }

    @Data
    static class CategoriesResponse {
        private Categories data;
        @Data
        class Categories {
            @JsonProperty("getCategories")
            private List<String> categories;
        }

    }
}
