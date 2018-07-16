package com.xoriant.restfulwebservicesmicroservices.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import java.net.URI;
import java.util.List;

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

import com.xoriant.restfulwebservicesmicroservices.model.User;
import com.xoriant.restfulwebservicesmicroservices.repository.UserDaoService;

@RestController
public class UserController {
    
	@Autowired
	UserDaoService userDaoService;
	
	@GetMapping("/users")
	public List<User> retrieveAllUsers(){
		return userDaoService.findAll();
	}
	
	@GetMapping("/users/{userId}")
	public Resource<User> getUser(@PathVariable int userId) {
		User user = userDaoService.findOne(userId);
		if( user == null)
			throw new UserNotFoundException("id:" + userId);
		
		//HATEOAS
		Resource<User> resource = new Resource<User>(user);
		ControllerLinkBuilder links = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		
		resource.add(links.withRel("all-users"));
		
		return resource;
	}
	
	

	@PostMapping(value="/users")
	@ResponseStatus(value=HttpStatus.CREATED) // this is optional as we are already sending status through "created" method of ResponseEntity
	public ResponseEntity<Object> loadUser(@Valid @RequestBody User user) {
		User savedUser =  userDaoService.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(savedUser.getUserId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<Object> removeUser(@PathVariable int userId) {
		User user = userDaoService.deleteUser(userId);
		if( user == null)
			throw new UserNotFoundException("id:" + userId);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		
	}
	
}
