package com.fjx.oa.service.aop;

import org.aspectj.lang.JoinPoint;

import com.fjx.oa.models.Organization;

public class OrganizationServiceAop {

	public void addOrgAfter(JoinPoint joinPoint) throws Throwable {
		Object[] args = joinPoint.getArgs();
		for (int i = 0; i < args.length; i++) {
			if(args[i] instanceof Organization){
				System.out.println(((Organization)args[i]).getName());
			}else{
				System.out.println(args[i]);
			}
		}
		System.out.println(joinPoint.getSignature().getName());
	}
}
