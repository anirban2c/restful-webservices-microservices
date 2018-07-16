package com.xoriant.restfulwebservicesmicroservices.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.xoriant.restfulwebservicesmicroservices.model.Post;
import com.xoriant.restfulwebservicesmicroservices.model.User;
import com.xoriant.restfulwebservicesmicroservices.repository.PostRepository;
import com.xoriant.restfulwebservicesmicroservices.repository.UserDaoService;
import com.xoriant.restfulwebservicesmicroservices.repository.UserRepository;

@RestController
public class UserJpaController {
    
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PostRepository postRepository;
	
	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers(){
		return userRepository.findAll();
	}
	
	@GetMapping("/jpa/users/{userId}")
	public Resource<User> getUser(@PathVariable int userId) {
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent())
			throw new UserNotFoundException("id:" + userId);
		
		//HATEOAS
		Resource<User> resource = new Resource<User>(user.get());
		ControllerLinkBuilder links = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		
		resource.add(links.withRel("all-users"));
		
		return resource;
	}
	
	

	@PostMapping(value="/jpa/users")
	@ResponseStatus(value=HttpStatus.CREATED) // this is optional as we are already sending status through "created" method of ResponseEntity
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser =  userRepository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(savedUser.getUserId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/jpa/users/{userId}")
	public void removeUser(@PathVariable int userId) {
		userRepository.deleteById(userId);
//		if( user == null)
//			throw new UserNotFoundException("id:" + userId);
//		
//		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		
		
	}
	
	@GetMapping("/jpa/users/{userId}/posts")
	public List<Post> getPost(@PathVariable int userId) {
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent())
			throw new UserNotFoundException("id:" + userId);
		
		
		return user.get().getPosts();
	}
	
	@PostMapping(value="/jpa/users/{userId}/posts")
	public ResponseEntity<Object> createPost(@Valid @PathVariable int userId, @RequestBody Post post) {
		
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent())
			throw new UserNotFoundException("id:" + userId);
		
		post.setUser(user.get());
		
		postRepository.save(post);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(post.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	
}
