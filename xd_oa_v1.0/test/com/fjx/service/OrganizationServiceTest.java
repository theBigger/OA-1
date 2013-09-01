package com.fjx.service;

import javax.xml.registry.infomodel.Organization;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-applicationContext.xml")
public class OrganizationServiceTest {
	
	@Autowired
	private Organization organization;
	
	@Test
	public void Test1(){
		System.out.println(organization);
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	
	
}
