package com.xoriant.restfulwebservicesmicroservices.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.xoriant.restfulwebservicesmicroservices.model.HelloWorldBean;

@RestController
public class HelloWorldController {
	
	@Autowired
	MessageSource messageSource;
	
	//Instead of @RequestMapping there is also a shortcut i.e @GetMapping. In this case we won't write the GET method.
	@GetMapping(value="/helloworld")
	public String helloWorld() {
		return "Hello-World";
	}
	
	@GetMapping("/helloworld-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello Bean");
	}
	
	@GetMapping(path="/helloworld/path-var/{var}")
	public HelloWorldBean helloWorldPathVariable(@PathVariable String var) {
		return new HelloWorldBean("Hello World: " + var);
	}
	
	@GetMapping("/helloworld-internationalized")
	public String HelloWorldInternationalized () {
		return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
	}
	
	

}
