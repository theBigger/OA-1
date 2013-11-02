package com.fjx.service;


import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fjx.oa.service.IModuleService;
import com.fjx.oa.service.IOrganizationService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-applicationContext.xml")
public class ModuleServiceTest {
	
	@Autowired
	private IModuleService moduleService ;
	
	@Test
	public void testTreeGrid() throws Exception{
		List<Map<String,Object>> orgs = moduleService.treeGrid4ListMap(1);
		for(Map org : orgs){
			System.out.println(org.get("name"));
		}
		
	}

	
}
