package com.fjx.oa.security.service.impl;

import java.util.Map;

import org.apache.struts2.json.JSONUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fjx.oa.security.service.IAuthenUrlService;
import com.fjx.oa.vo.EasyUIPagination;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-applicationContext.xml")
public class AuthenUrlServiceTest {
	
	@Autowired
	private IAuthenUrlService authenUrlService;
	
	@Test
	public void testQueryPage() throws Exception {
		try {
			EasyUIPagination<Map<String, Object>> page = authenUrlService.queryPage("1");
			System.out.println(JSONUtil.serialize(page));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("error...");
		}
	}

}
