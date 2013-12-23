package com.fjx.oa.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fjx.oa.service.IDictiService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-applicationContext.xml")
public class DictServiceTest {
	
	@Autowired
	private IDictiService dictiService;
	
	
	@Test
	public void testGetDictByFlag() throws Exception {
		dictiService.getDictByFlag("permission");
	}
	
	@Test
	public void testLoadDict() throws Exception {
		dictiService.loadDict();
	}
	
}
