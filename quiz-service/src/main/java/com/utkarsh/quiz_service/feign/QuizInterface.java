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

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {
    @GetMapping("question/getQuizQuestionIds")
    public ResponseEntity<List<Integer>> getQuizQuestionIds(@RequestParam String category,
                                                            @RequestParam int num);

    @PostMapping("question/getQuizQuestions")
    public ResponseEntity<List<QuestionDTO>> getQuizQuestions(@RequestBody List<Integer> questionIds);

    @PostMapping("question/getQuizScore")
    public ResponseEntity<Integer> getQuizScore(@RequestBody List<Response> responses);
}
