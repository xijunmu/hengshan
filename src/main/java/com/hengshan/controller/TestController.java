package com.hengshan.controller;

import com.hengshan.common.annotation.LogAop;
import com.hengshan.common.annotation.NotResponseAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/error")
    public int error() {
        return (9/0);
    }

    @RequestMapping("/test")
    @NotResponseAdvice
    @LogAop
    public int test() {
        return 1;
    }

    @RequestMapping("/test1")
    public int test1() {
        return 1;
    }

    @RequestMapping("/test3")
    public String string() {
        return "hello";
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
    }
}