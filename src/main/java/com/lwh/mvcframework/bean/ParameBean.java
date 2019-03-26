package com.lwh.mvcframework.bean;

import com.lwh.mvcframework.annotation.GPRequestParam;

public class ParameBean {
    private String name;
    private GPRequestParam param;

    public GPRequestParam getParam() {
        return param;
    }

    public void setParam(GPRequestParam param) {
        this.param = param;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
