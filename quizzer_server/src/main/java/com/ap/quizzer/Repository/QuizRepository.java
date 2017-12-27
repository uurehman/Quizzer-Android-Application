package com.ap.quizzer.Repository;

import com.ap.quizzer.Model.Quiz;
import org.springframework.data.repository.CrudRepository;

public interface QuizRepository extends CrudRepository<Quiz,Integer> {
}
