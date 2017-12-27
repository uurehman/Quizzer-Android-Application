package com.ap.quizzer.Repository;

import com.ap.quizzer.Model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,String> {
}
