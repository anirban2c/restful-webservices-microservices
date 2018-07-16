package com.xoriant.restfulwebservicesmicroservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xoriant.restfulwebservicesmicroservices.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{

}
