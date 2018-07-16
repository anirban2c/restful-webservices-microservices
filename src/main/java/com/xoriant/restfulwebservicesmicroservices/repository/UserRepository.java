package com.xoriant.restfulwebservicesmicroservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xoriant.restfulwebservicesmicroservices.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

}
