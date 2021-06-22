package com.springtutorial.springsecurityjpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springtutorial.springsecurityjpa.models.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	Optional<User> findByUserName(String userName);
}
