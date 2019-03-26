package com.lwh.mvcframework.utils;

import com.lwh.mvcframework.annotation.GPRequestParam;
import com.lwh.mvcframework.bean.ParameBean;
import com.lwh.mvcframework.bean.RequestBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ParameterNameUtils {
    /**
     * 获取指定方法的参数名
     *
     * @param method 要获取参数名的方法
     * @return key 作为参数名称 value是参数类型 后面强转使用
     */
    public static Map<String, ParameBean> getMethodParameterNamesByAnnotation(Method method) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        if (parameterAnnotations == null || parameterAnnotations.length == 0) {
            return null;
        }
        Map<String, ParameBean> parameterNames = new LinkedHashMap<>();
//        method.getParameters();
//        parameterNames.put("HttpServletRequest", null);
//        parameterNames.put("HttpServletResponse", null);
        for (Parameter p : method.getParameters()) {
            for (Annotation annotation : p.getAnnotations()) {
                if (annotation instanceof GPRequestParam) {
//                    System.out.println("parameter: " + p.getType().getName() + ", " + p.getName());
//                    System.out.println(annotation.annotationType().getName() + "======================");
                    GPRequestParam param = (GPRequestParam) annotation;
                    ParameBean parameBean = new ParameBean();
                    parameBean.setName(p.getType().getName());
                    parameBean.setParam(param);
                    parameterNames.put(param.name(), parameBean);
                    break;
                }
            }
        }

        return parameterNames;
    }

    /**
     * 获取指定方法的参数名
     *
     * @param method 要获取参数名的方法
     * @return key 作为参数名称 value是参数类型 后面强转使用
     */
    public static List<RequestBean> getMethodParameterNamesByAnnotationV2(Method method) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        if (parameterAnnotations == null || parameterAnnotations.length == 0) {
            return null;
        }
        List<RequestBean> parameterNames = new ArrayList<>();
        for (Parameter p : method.getParameters()) {
            GPRequestParam gpRequestParam = p.getAnnotation(GPRequestParam.class);
            RequestBean parameBean = new RequestBean();
            parameBean.setGetParameterType(p.getType().getName());
            if (gpRequestParam != null) {
                //是注解类型的参数
                parameBean.setParam(gpRequestParam);
                parameBean.setParameterName(gpRequestParam.name());
            }
            parameterNames.add(parameBean);
        }

        return parameterNames;
    }

    /**
     * 代码改进  因为不知道各个参数的位置所以我决定开始写个自适应的
     *
     * @param pa
     * @param mParames
     * @param InParams
     * @return
     */
    public static Object[] wiredParameter(Object[] pa, Map<String, ParameBean> mParames, Map<String, String[]> InParams) {
        int index = 2;
        for (Map.Entry<String, ParameBean> entry : mParames.entrySet()) {
            String[] ins = InParams.get(entry.getKey());
            String data = entry.getValue().getParam().defaultValue();
//            if (ins != null && ins.length > 0) {
//                data = ins[0];
//            }
            data = dismantle(ins, data);
            if (entry.getValue().getName().contains("int") || entry.getValue().getName().contains("Integer")) {
                pa[index] = Integer.valueOf(data);
            } else {
                pa[index] = data;
            }
            index++;
        }
        return pa;
    }

    /**
     * @param req
     * @param resp
     * @param InParams 实参
     * @return 支持多参数，位置可变
     */
    public static Object[] wiredParameterV2(Method method, HttpServletRequest req, HttpServletResponse resp, Map<String, String[]> InParams) {
//        Map<String, ParameBean> mParames = getMethodParameterNamesByAnnotation(method);
        List<RequestBean> mParames = getMethodParameterNamesByAnnotationV2(method);
        Object[] pa = new Object[mParames.size()];
        for (int i = 0; i < mParames.size(); i++) {
            RequestBean requestBean = mParames.get(i);
            String typeName = requestBean.getGetParameterType();//参数类型名称
            String data = "";
            if (requestBean.getParam() != null) {
                //说明这个是有加注解的 一定有参数名称
                String[] ins = InParams.get(requestBean.getParameterName());
                data = requestBean.getParam().defaultValue();//先得到默认值
                data = dismantle(ins, data);//支持多参数
            } else {

            }
            if (typeName.contains("int") || typeName.contains("Integer")) {
                pa[i] = Integer.valueOf(data);
            } else if (typeName.contains("HttpServletRequest")) {
                pa[i] = req;

            } else if (typeName.contains("HttpServletResponse")) {
                pa[i] = resp;
            } else {
                pa[i] = data;
            }
        }

        return pa;
    }

    private static String dismantle(String[] inStr, String defaultStr) {
        String result = "";
        if (inStr != null && inStr.length > 0) {
            for (int i = 0; i < inStr.length; i++)
                result = result + inStr[i] + ",";
        }
        if (result.length() > 0)
            result = result.substring(0, result.length() - 1);
        else
            return defaultStr;
        return result;
    }
}
