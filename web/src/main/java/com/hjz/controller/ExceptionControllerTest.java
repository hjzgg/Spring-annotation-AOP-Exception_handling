package com.hjz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hjz.service.ExceptionServiceTest;

@Controller
@RequestMapping("/exception")
public class ExceptionControllerTest {
	
	@Autowired
	private ExceptionServiceTest service;
	
	@RequestMapping("/test")
	@ResponseBody
	public JSONObject test() {
		JSONObject ans = new JSONObject();
		
		try {
			service.test();
		} catch (Exception e) {
			
		}
		
//		boolean flag = true;
//		if(flag) {
//			throw new BusinessException("我是业务异常-->contorller");
//		}
		
//		try {
//			boolean flag = false;
//			if(flag) {
//				throw new BusinessException("我是业务异常-->contorller");
//			}
//		} catch(BusinessException e) {
//			ans.put("msg", e.getMessage());
//		} 
		
		return ans;
	}
}
