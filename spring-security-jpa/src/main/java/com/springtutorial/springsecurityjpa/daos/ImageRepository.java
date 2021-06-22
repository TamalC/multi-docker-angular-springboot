package com.springtutorial.springsecurityjpa.daos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springtutorial.springsecurityjpa.models.ImageModel;

public interface ImageRepository extends JpaRepository<ImageModel, Integer>{
	Optional<ImageModel> findByName(String name);
}
