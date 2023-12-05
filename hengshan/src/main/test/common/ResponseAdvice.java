package com.hengshan.common;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.hengshan.common.ResultBody;
import com.hengshan.common.annotation.NotResponseAdvice;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


/**
 * 全局统一返回包装
 */
@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        //如果接口上添加@NotResponseAdvice则不对返回结果做包装
        return !returnType.hasMethodAnnotation(NotResponseAdvice.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        //处理字符串类型数据
        if (body instanceof String) {
            return JSONObject.toJSONString(ResultBody.success(body));
        }
        //返回类型如果已经是封装类型则直接返回
        if (body instanceof ResultBody) {
            return body;
        }
        return ResultBody.success(body);
    }
}
