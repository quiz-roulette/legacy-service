package com.quizroulette.LegacyServer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.quizroulette.LegacyServer.question_bank_adapter.CategoryQuizMappingProperties;
import com.quizroulette.LegacyServer.user_adapter.User;
import com.quizroulette.LegacyServer.user_adapter.UserClient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {

  private CategoryQuizMappingProperties categoryQuizMappingProperties;
  private UserClient userClient;

  @GetMapping("/GetQuizUserRank")
  public QuizUserRank getQuizUser(@RequestParam("quizid") String quizId,
      @RequestParam("quizuserid") String userid) {
    return new QuizUserRank("1");
  }

  @GetMapping("/getQuizUserAvatar")
  public String getQuizUserAvatar(@RequestParam("QuizUserId") String quizUserId) {
    return userClient.getUserByIdAsync(quizUserId).block().getAvatarUrl();
  }

  @PostMapping("/quiz_token")
  public boolean addQuizUser(@RequestBody QuizTokenInput quizTokenInput) {
    User user = new User();
    user.setUsername(quizTokenInput.getToken()+"_"+quizTokenInput.getQuizUserId());
    user.setPassword(quizTokenInput.getToken());
    userClient.addUser(user);
    return true;
  }

  @GetMapping("/quiz_token")
  public QuizTokenOutcome getQuizToken(@RequestParam("quizId") String quizId) {
    if (categoryQuizMappingProperties.getQuizCategoryMapping().get(quizId) != null) {
      return new QuizTokenOutcome(quizId,
          categoryQuizMappingProperties.getQuizCategoryMapping().get(quizId));
    }
    return new QuizTokenOutcome(quizId, "Math");
  }

  @PatchMapping("/quizuser")
  public void addQuizUserAvatar(@RequestBody QuizUserAvatarInput quizUserAvatarInput) {
    User user = userClient.getUserByIdAsync(quizUserAvatarInput.quizUserId).block();
    user.setAvatarUrl(quizUserAvatarInput.getAvatar());
    userClient.updateUser(user);
  }

  @AllArgsConstructor
  @Getter
  static class QuizUserRank {
    private String rank;
  }

  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  static class QuizTokenInput {

    @JsonProperty("QuizUserId")
    private String quizUserId;
    @JsonProperty("Token")
    private String token;
  }

  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  static class QuizTokenOutcome {

    @JsonProperty("QuizId")
    private String quizId;
    @JsonProperty("CategoryName")
    private String categoryName;
  }

  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  static class QuizUserAvatarInput {

    @JsonProperty("QuizUserId")
    private String quizUserId;
    @JsonProperty("Avatar")
    private String avatar;
  }
}
