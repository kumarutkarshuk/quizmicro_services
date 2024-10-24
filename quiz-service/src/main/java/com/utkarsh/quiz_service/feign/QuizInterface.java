package com.utkarsh.quiz_service.feign;

import com.utkarsh.quiz_service.model.QuestionDTO;
import com.utkarsh.quiz_service.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// application-name of Feign server app in uppercase
@FeignClient("QUESTION-SERVICE")
// contains required same method definitions with complete routes in the annotations
public interface QuizInterface {
    @GetMapping("question/getQuizQuestionIds")
    public ResponseEntity<List<Integer>> getQuizQuestionIds(@RequestParam String category,
                                                            @RequestParam int num);

    @PostMapping("question/getQuizQuestions")
    public ResponseEntity<List<QuestionDTO>> getQuizQuestions(@RequestBody List<Integer> questionIds);

    @PostMapping("question/getQuizScore")
    public ResponseEntity<Integer> getQuizScore(@RequestBody List<Response> responses);
}
