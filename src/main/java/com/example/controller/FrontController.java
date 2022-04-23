package com.example.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/front")
public class FrontController {

    @RequestMapping("/")
    public String getFrontPage(){

        return "/page/front/work.html";
    }
}
