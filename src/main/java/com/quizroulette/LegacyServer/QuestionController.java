package com.quizroulette.LegacyServer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.quizroulette.LegacyServer.question_bank_adapter.ChoiceDTO;
import com.quizroulette.LegacyServer.question_bank_adapter.CorrectChoiceDTO;
import com.quizroulette.LegacyServer.question_bank_adapter.QuestionBankAdapter;
import com.quizroulette.LegacyServer.question_bank_adapter.QuestionDTO;

import java.util.List;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
public class QuestionController {

    private QuestionBankAdapter questionBankAdapter;

    @GetMapping("/getQuestionCountForQuiz")
    public List<QuestionCount> getQuestionCount(@RequestParam("QuizId") String quizId) {
        return List.of(new QuestionCount(questionBankAdapter.getQuestions(quizId).size()));
    }

    @GetMapping("/question")
    public List<QuestionDTO> getQuestions(@RequestParam(value = "userid", required = false) String userId,
                                          @RequestParam(value = "quizid", required = false) String quizId) {
        if(quizId == null) return questionBankAdapter.getQuestions();
        return questionBankAdapter.getQuestions(quizId);
    }



    @PutMapping("/question")
    public void deleteQuestion(@RequestBody DeleteQuestionDTO deleteQuestionDTO){
        log.info("Deleting question .... {}", deleteQuestionDTO);
    }

    @GetMapping("/choice")
    public List<ChoiceDTO> getChoices() {
        return questionBankAdapter.getChoices();
    }

    @GetMapping("/correctchoice")
    public List<CorrectChoiceDTO> getCorrectChoice() {
        return questionBankAdapter.getCorrectChoices();
    }

    @GetMapping("/correctChoice")
    public List<CorrectChoiceDTO> getCorrectChoicev1() {
        return questionBankAdapter.getCorrectChoices();
    }

    @GetMapping("/category")
    public List<String> getCategories() {
        return questionBankAdapter.getCategories();
    }

    @PostMapping("/category")
    public void addCategory(@RequestBody CategoryDTO categoryDTO){
        log.info("adding category but not implemented ... {}", categoryDTO);
    }

    @DeleteMapping("/category")
    public void deleteCategory(@RequestParam("CategoryName") String categoryName){
        log.info("deleting category name... {}", categoryName);
    }

    @PostMapping("/questionwrapper")
    public void addQuestionWrapper(@RequestBody QuestionWrapperDTO questionWrapperDTO){
        log.info("adding question wrapper ... {}", questionWrapperDTO);
    }

    @GetMapping("/getQuestionCountForCategory")
    public QuestionCount retrieveCount(){
        return new QuestionCount(0);
    }

    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @ToString
    static class QuestionWrapperDTO {
        @JsonProperty("QuestionId")
        private int questionId;
        @JsonProperty("Text")
        private String text;
        @JsonProperty("CategoryName")
        private String categoryName;
        @JsonProperty("ImageUrl")
        private String imageUrl;
        @JsonProperty("choiceDTO")
        private ChoiceDTO choiceDTO;
        @JsonProperty("correctChoiceDTO")
        private CorrectChoiceDTO correctChoiceDTO;
        @JsonProperty("correctChoiceText")
        private String correctChoiceText;
    }


    @Getter
    @AllArgsConstructor
    static class QuestionCount {

        @JsonProperty("Count")
        Integer count;
    }

    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @ToString
    static class CategoryDTO {
        @JsonProperty("CategoryName")
        private String categoryName;
    }


    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @ToString
    static class DeleteQuestionDTO {
        @JsonProperty("QuestionId")
        private String questionId;
        @JsonProperty("IsDelete")
        private boolean isDelete;
    }


}
