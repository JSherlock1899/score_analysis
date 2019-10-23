package com.slxy.analysis.student.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/st")
@Controller
public class AnalyizeController {

    @RequestMapping("/analyize")
    public String FirstAnalyize(){
        return "student/analyze";
    }
}
