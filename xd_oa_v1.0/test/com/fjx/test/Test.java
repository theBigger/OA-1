package com.fjx.test;



public class Test {
	
	
	public static void main(String[] args) {
		String val = ".*oa|.*\\.jsp";
		System.out.println("/oa/org/view.jsp".matches(val));
		String val2 = ".*_page.*";
		System.out.println("/oa/org/org_page".matches(val2));
	}
	
}
