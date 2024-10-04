package com.utkarsh.quiz_service.service;


import com.utkarsh.quiz_service.feign.QuizInterface;
import com.utkarsh.quiz_service.model.QuestionDTO;
import com.utkarsh.quiz_service.model.Quiz;
import com.utkarsh.quiz_service.model.Response;
import com.utkarsh.quiz_service.repo.QuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private QuizRepo quizRepo;

    @Autowired
    // @EnableFeignClients on the class containing main method
    private QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int num, String title) {
        try{
            List<Integer> questionIds = quizInterface.getQuizQuestionIds(category, num).getBody();
            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setQuestionIds(questionIds);

            quizRepo.save(quiz);

            return new ResponseEntity<>("Quiz created successfully", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Error creating quiz", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<QuestionDTO>> getQuizById(int id) {
        try{
            Optional<Quiz> optionalQuiz = quizRepo.findById(id);

            // better to explicitly check for convenience
            // keep try-catch for unexpected runtime exceptions
            if(optionalQuiz.isEmpty()){
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
            }

            return quizInterface.getQuizQuestions(optionalQuiz.get().getQuestionIds());

        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Integer> getQuizScore(List<Response> quizResponseList) {
        try{

            return quizInterface.getQuizScore(quizResponseList);

        } catch (Exception e) {
            return new ResponseEntity<>(-1, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
