package com.utkarsh.question_service.controller;


import com.utkarsh.question_service.model.Question;
import com.utkarsh.question_service.model.QuestionDTO;
import com.utkarsh.question_service.model.Response;
import com.utkarsh.question_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getQuestions(){
        return questionService.getQuestions();
    }

    @GetMapping("/category/{cat}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable("cat") String category){
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);
    }

    // NEW CONTROLLERS FOR QUESTION MICROSERVICE

    // generate ques for quiz
    @GetMapping("getQuizQuestionIds")
    public ResponseEntity<List<Integer>> getQuizQuestionIds(@RequestParam String category,
                                             @RequestParam int num){
        return questionService.getQuizQuestionIds(category, num);
    }

    // get questions from list of ques ids
    @PostMapping("getQuizQuestions")
    public ResponseEntity<List<QuestionDTO>> getQuizQuestions(@RequestBody List<Integer> questionIds){
        return questionService.getQuizQuestions(questionIds);
    }

    // calc quiz score
    @PostMapping("getQuizScore")
    public ResponseEntity<Integer> getQuizScore(@RequestBody List<Response> responses){
        return questionService.getQuizScore(responses);
    }
}
