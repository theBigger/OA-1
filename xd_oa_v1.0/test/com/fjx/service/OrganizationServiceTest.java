package com.fjx.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fjx.oa.service.IOrganizationService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-applicationContext.xml")
public class OrganizationServiceTest {
	
	@Autowired
	private IOrganizationService organizationService ;
	
	@Test
	public void testTreeGrid() throws Exception{
		organizationService.treeGrid(0);
		
		
	}

	
}
