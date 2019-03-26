package com.lwh.demo.mvc.action;

import com.lwh.demo.service.impl.DemoService;
import com.lwh.mvcframework.annotation.GPAutowired;
import com.lwh.mvcframework.annotation.GPController;
import com.lwh.mvcframework.annotation.GPRequestMapping;
import com.lwh.mvcframework.annotation.GPRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//虽然，用法一样，但是没有功能
@GPController
@GPRequestMapping("/demo")
public class DemoAction {

    @GPAutowired
    private DemoService demoService;

    @GPRequestMapping("/query")
    public void query(HttpServletRequest req, HttpServletResponse resp,
                      @GPRequestParam(name = "name", defaultValue = "0") String name, @GPRequestParam(name = "age") Integer age, @GPRequestParam(name = "mobile", defaultValue = "110") String mobile) {
//		String result = demoService.get(name);
        String result = "My name is " + name + " age : " + age + " mobile: " + mobile;
        try {
            resp.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @GPRequestMapping("/test")
    public void query( HttpServletResponse resp,
                      @GPRequestParam(name = "name", defaultValue = "0") String name,HttpServletRequest req, @GPRequestParam(name = "age") Integer age, @GPRequestParam(name = "mobile", defaultValue = "110") String mobile) {
//		String result = demoService.get(name);
        String result = "My name is " + name + " age : " + age + " mobile: " + mobile;
        try {
            resp.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @GPRequestMapping("/add")
    public void add(HttpServletRequest req, HttpServletResponse resp,
                    @GPRequestParam(name = "a") Integer a, @GPRequestParam(name = "b") Integer b) {
        try {
            resp.getWriter().write(a + "+" + b + "=" + (a + b));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GPRequestMapping("/remove")
    public void remove(HttpServletRequest req, HttpServletResponse resp,
                       @GPRequestParam(name = "id") Integer id) {
    }

}
