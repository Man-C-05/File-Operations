package com.crud.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.crud.entity.Mansi;

public interface DocumentRepo extends MongoRepository<Mansi, String> {

	Optional<Mansi> findByFileName(String fileName);

	

}
