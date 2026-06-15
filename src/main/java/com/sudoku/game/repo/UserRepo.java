package com.sudoku.game.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sudoku.game.model.User;

public interface  UserRepo extends MongoRepository<User, String> {

    Optional<User> findByUsername(String Username);
    
}
