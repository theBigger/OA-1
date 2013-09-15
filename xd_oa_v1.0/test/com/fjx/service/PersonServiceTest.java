package com.fjx.service;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fjx.oa.service.IPersonService;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-applicationContext.xml")
public class PersonServiceTest {
	
	@Autowired
	private IPersonService personService;
	
	
	@Test
	public void testQueryPersons(){
		try {
			personService.queryPersons("冯", new Date(), new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
