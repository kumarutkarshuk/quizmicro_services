package com.utkarsh.quiz_service.controller;


import com.utkarsh.quiz_service.model.QuestionDTO;
import com.utkarsh.quiz_service.model.Response;
import com.utkarsh.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category,
                                             @RequestParam int num,
                                             @RequestParam String title){
        return quizService.createQuiz(category, num, title);
    }

    @GetMapping("getQuiz/{id}")
    public ResponseEntity<List<QuestionDTO>> getQuizById(@PathVariable int id){
        return quizService.getQuizById(id);
    }

    // quiz id not involved here
    @PostMapping("getScore")
    public ResponseEntity<Integer> getQuizScore(@RequestBody List<Response> quizResponseList){
        return quizService.getQuizScore(quizResponseList);
    }
}
