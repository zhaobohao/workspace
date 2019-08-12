package com.spring.web.test.controller;

import com.spring.web.test.service.CacheService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HelloController {

    @Resource
    CacheService cacheService;
    @GetMapping("/hello")
    public String hello()
    {
        return cacheService.getName();
    }

}
