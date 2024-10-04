package com.utkarsh.question_service.repo;


import com.utkarsh.question_service.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Integer> {
    List<Question> findByCategory(String category);

    @Query("select q.id from Question q where q.category = :category order by random() limit :num")
    List<Integer> getRandomQuestionsByCategoryAndNum(String category, int num);
}
