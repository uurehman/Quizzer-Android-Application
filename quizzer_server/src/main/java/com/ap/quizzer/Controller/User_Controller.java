package com.ap.quizzer.Controller;

import com.ap.quizzer.Repository.UserRepository;
import com.ap.quizzer.Model.User;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class User_Controller{
	private final UserRepository repository;

	@Autowired
	public User_Controller(UserRepository repository) {
		this.repository = repository;
	}


	@RequestMapping(method = RequestMethod.POST, value = "/sign_up")
	//method to save User
	public boolean add_user(@RequestBody User u){
		if(repository.exists(u.getUsername()))
			return false;
		else {
			repository.save(u);
			return true;
		}
	}

	//method to return one User of given id
	@RequestMapping(value = "/{id}")
	public User getById(@PathVariable String id){
		return repository.findOne(id);
	}

	//method to return all Users
	@RequestMapping
	public List<User> getUsers(){
		return (List<User>) repository.findAll();
	}

	@RequestMapping(method = RequestMethod.POST,value = "/sign_in")
	public int sign_in(@RequestBody User current){
		User u = getById(current.getUsername());
		if(u!=null&&u.getPassword().equals(current.getPassword())) {
			if(u.getRole()) return 2;   			//User is a student
			else return 1;                           //User is an instructor
		}
		else return 0; //Authentication Error
	}

}
