package com.utkarsh.question_service.service;


import com.utkarsh.question_service.model.Question;
import com.utkarsh.question_service.model.QuestionDTO;
import com.utkarsh.question_service.model.Response;
import com.utkarsh.question_service.repo.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class QuestionService {

    // setter-based injection is a better practice
    @Autowired
    private QuestionRepo questionRepo;

    // for testing load balancing which is done automatically via OpenFeign
    @Autowired
    private Environment environment;

    public ResponseEntity<List<Question>> getQuestions() {
        try{
            return new ResponseEntity<>(questionRepo.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try{
            return new ResponseEntity<>(questionRepo.findByCategory(category), HttpStatus.OK);
        } catch (Exception e) {
            // good practice to return empty instance instead of null
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> addQuestion(Question question) {

        try{
            questionRepo.save(question);
            return new ResponseEntity<>("Question added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Could not add question", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Integer>> getQuizQuestionIds(String category, int num) {
        try{
            List<Integer> questionIdList = questionRepo.getRandomQuestionsByCategoryAndNum(category, num);

            return new ResponseEntity<>(questionIdList, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<QuestionDTO>> getQuizQuestions(List<Integer> questionIds) {

        System.out.println("port: " + environment.getProperty("local.server.port"));

        try{
            List<Question> questionList = questionRepo.findAllById(questionIds);
            List<QuestionDTO> questionDTOList = new ArrayList<>();

            for(Question q : questionList) {
                QuestionDTO questionDTO = QuestionDTO.builder()
                        .id(q.getId())
                        .questionTitle(q.getQuestionTitle())
                        .option1(q.getOption1())
                        .option2(q.getOption2())
                        .option3(q.getOption3())
                        .option4(q.getOption4())
                        .build();
                questionDTOList.add(questionDTO);
            }

            return new ResponseEntity<>(questionDTOList, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Integer> getQuizScore(List<Response> responses) {
        try{
            List<Integer> questionIdList = responses.stream().map(d -> d.getId()).toList();
            List<Question> questionList = questionRepo.findAllById(questionIdList);
            HashMap<Integer, String> questionIdToAns = new HashMap<>();
            for(Question q : questionList) {
                questionIdToAns.put(q.getId(), q.getRightAnswer());
            }

            int totalScore = 0;
            for(Response r : responses) {
                if(r.getResponse().equals(questionIdToAns.get(r.getId()))) {
                    totalScore++;
                }
            }

            return new ResponseEntity<>(totalScore, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(-1, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
