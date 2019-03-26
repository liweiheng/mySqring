package com.lwh.demo.service.impl;

import com.lwh.demo.service.IDemoService;
import com.lwh.mvcframework.annotation.GPService;

/**
 * 核心业务逻辑
 */
@GPService
public class DemoService implements IDemoService{

	public String get(String name) {
		return "My name is " + name;
	}

}
