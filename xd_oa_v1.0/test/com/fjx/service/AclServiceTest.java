package com.fjx.service;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fjx.oa.security.service.IAclService;
import com.fjx.oa.security.service.impl.AclService;
import com.fjx.oa.service.IPersonService;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-applicationContext.xml")
public class AclServiceTest {
	
	@Autowired
	private IAclService aclService;
	
	
	@Test
	public void testAddOrUpdatePermission(){
		try {
			aclService.addOrUpdatePermission("role", Long.parseLong("1"), Long.parseLong("2"), 1, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDelPermission(){
		try {
			aclService.delPermission("role", Long.parseLong("1"), Long.parseLong("1"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testSearchAclRecord(){
		try {
			aclService.searchAclRecord("role", (long) 1);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
