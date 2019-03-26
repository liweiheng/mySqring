package com.lwh.mvcframework.bean;

import com.lwh.mvcframework.annotation.GPRequestParam;

public class RequestBean {
    private String parameterName;//参数名称
    private String getParameterType;//参数类型
    private GPRequestParam param;

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getGetParameterType() {
        return getParameterType;
    }

    public void setGetParameterType(String getParameterType) {
        this.getParameterType = getParameterType;
    }

    public GPRequestParam getParam() {
        return param;
    }

    public void setParam(GPRequestParam param) {
        this.param = param;
    }
}
