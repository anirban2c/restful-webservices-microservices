package com.xoriant.restfulwebservicesmicroservices.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.xoriant.restfulwebservicesmicroservices.model.SomeBean;

@RestController
public class FilterController {
	
	@GetMapping(value="/filtering" , produces = MediaType.APPLICATION_JSON_VALUE)
	public MappingJacksonValue testFiltering() {
		SomeBean someBean = new SomeBean("val1","val2","val3");
		
		//Filter all except field1 and field2
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1","field2");
		FilterProvider filters =  new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		
		
		MappingJacksonValue mapping = new MappingJacksonValue(someBean);
		mapping.setFilters(filters);
		
		return mapping;
	}
	
	@GetMapping(value="/filtering-list" , produces = MediaType.APPLICATION_JSON_VALUE)
	public MappingJacksonValue testFilteringList() {
//		SomeBean someBean1 = new SomeBean("val1","val2","val3");
//		SomeBean someBean2 = new SomeBean("val12","val22","val32");
//		List<SomeBean> someBeanList = new ArrayList();
//		someBeanList.add(someBean1);
//		someBeanList.add(someBean2);
		
		List<SomeBean> someBeanList = Arrays.asList(new SomeBean("val1","val2","val3"),new SomeBean("val12","val22","val32"));
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field2","field3");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(someBeanList);
        mapping.setFilters(filters);
        
		return mapping;
		
//		return someBeanList;
	}

}
