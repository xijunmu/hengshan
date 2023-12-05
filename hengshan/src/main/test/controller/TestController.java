package com.hengshan.controller;

import com.hengshan.common.annotation.NotResponseAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/error")
    @ResponseBody
    public int error() {
        return (9/0);
    }

    @RequestMapping("/test")
    @NotResponseAdvice
    @LogAop
    @ResponseBody
    public int test() {
        return 1;
    }

    @RequestMapping("/test1")
    @ResponseBody
    public int test1() {
        return 1;
    }

    @RequestMapping("/test2")
    @ResponseBody
    public String test2() {
        return "hello";
    }

}