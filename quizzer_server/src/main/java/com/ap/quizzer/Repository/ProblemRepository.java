package com.ap.quizzer.Repository;

import com.ap.quizzer.Model.Problem;
import org.springframework.data.repository.CrudRepository;

public interface ProblemRepository extends CrudRepository<Problem,Integer> {
}
