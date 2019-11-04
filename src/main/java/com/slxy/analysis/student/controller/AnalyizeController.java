package com.slxy.analysis.student.controller;

import com.slxy.analysis.student.service.imp.StServiceimp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/st")
@Controller
public class AnalyizeController {

    @Autowired
    StServiceimp st;

    /*
    get exam information and data
     */
    @RequestMapping("/analyize")
    public ModelAndView FirstAnalyize(ModelAndView mv,HttpServletRequest req,
                                      @RequestParam(defaultValue = "18_students_grades_20190728",name = "examName",required = false) String exam){
        return st.getSingleMAXandMINgrade(st.SearchOne(exam, req,mv),exam);
    }





}
