package com.hjz.service;

import org.springframework.stereotype.Service;

import com.hjz.exception.ServiceException;

@Service
public class ExceptionServiceTest {
	public void test() {
		boolean flag = true;
		if(flag) {
			throw new ServiceException("service 异常");
		}
	}
}
