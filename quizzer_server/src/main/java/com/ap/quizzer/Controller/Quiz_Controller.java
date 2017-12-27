package com.ap.quizzer.Controller;

import com.ap.quizzer.Model.Quiz;
import com.ap.quizzer.Repository.ProblemRepository;
import com.ap.quizzer.Repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/quiz")
public class Quiz_Controller {
	private final QuizRepository repository;
	private final ProblemRepository prob_repository;

	@Autowired
	public Quiz_Controller(QuizRepository repository, ProblemRepository prob_repository) {
		this.repository = repository;
		this.prob_repository = prob_repository;
	}

	@RequestMapping(method = RequestMethod.POST)
	//method to save Quiz
	public void saveQuiz(@RequestBody Quiz q){
		prob_repository.save(q.get_problems());
		repository.save(q);
	}

	@RequestMapping(value = "/{id}")
	//method to return one Quiz of given id
	public Quiz getById(@PathVariable int id){
		return repository.findOne(id);
	}

	@RequestMapping(value = "/all")
	//method to return all Quizzes
	public List<Quiz> getQuizzes(){
		return (List<Quiz>) repository.findAll();
	}

}
