package com.slxy.analysis.student.controller;

import com.slxy.analysis.student.service.imp.StServiceimp;
import com.slxy.analysis.teacher.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StServiceimp ssi;

   @RequestMapping("/mygrade")
    public ModelAndView selectontGrade(HttpServletRequest request, ModelAndView mv,
                              @RequestParam(defaultValue = "18_students_grades_20190728",name = "examName",required = false) String exam){
       Student student = (Student) request.getSession().getAttribute("student");
       ssi.SearchOne(exam, request,mv);
       mv.setViewName("student/inquire");
       return mv;
    }

    @RequestMapping("/basic")
    public String SearchStudentBasicByid(HttpServletRequest request, Map<String,Object> map){
        Student username = ssi.SeacheStudentBasic(request);
        System.out.println(username.toString());
        map.put("stb",username);
        return "student/information";

    }



}
