package com.quizroulette.LegacyServer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/quiz")
public class QuizController {
    @PostMapping("/")
    public void addQuiz(@RequestBody QuizDTO quizDTO) {
        log.info("adding quiz ... {}", quizDTO);
    }

    @GetMapping("/")
    public List<QuizDTO> getAllQuiz() {
        return List.of();
    }

    @PatchMapping("/")
    public void updateQuiz(@RequestBody QuizDTO quizDTO) {
        log.info("updating quiz... {}", quizDTO);
    }

    @PostMapping("/quiz_with_tokenised")
    public void addOneTimeQuiz(@RequestBody QuizDTO quizDTO){
        log.info("adding one time quoiz... {}", quizDTO);
    }

    @GetMapping("/quiz_with_tokenised")
    public List<QuizDTO> getOneTimeQuiz(){
        log.info("getting one time quiz...");
        return List.of();
    }

    @PatchMapping("/quiz_with_tokenised")
    public void updateOneTimeQuiz(@RequestBody QuizDTO quizDTO){
        log.info("updating quiz... {}", quizDTO);
    }

    @PostMapping("/setupControlledQuiz")
    public void setupControlledQuiz(@RequestBody ControlledQuizDTO controlledQuizDTO){
        log.info("setting up controlled quiz ... {}", controlledQuizDTO);

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @ToString
    static class QuizDTO {
        private String quizId;
        private String categoryName;
        private String adminId;
        private int questionCount;
        private Date startDateTime;
        private Date endDateTime;
        private boolean hasEnded;
        private String groupName;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @ToString
    static class ControlledQuizDTO {
        @JsonProperty("CategoryName")
        private String categoryName;
    }
}
