package com.fjx.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fjx.oa.security.service.IUserService;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-applicationContext.xml")
public class UserServiceTest {
	
	@Autowired
	private IUserService userService;
	
	
	@Test
	public void testQueryUsers(){
		try {
			userService.queryPageUsers();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
