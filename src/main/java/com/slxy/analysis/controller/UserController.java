package com.slxy.analysis.controller;

import com.slxy.analysis.model.ClassGrade;
import com.slxy.analysis.model.Student;
import com.slxy.analysis.service.StudentService;
import com.slxy.analysis.service.TeacherService;
import com.slxy.analysis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/8/31 17:10
 */

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    StudentService studentService;

    /**
     * 测试类
     * @param id 学生id
     * @return
     */
    @RequestMapping("test")
    @ResponseBody
    public Student test(Integer grade,String id){
        Student student = studentService.getStudentPassword(grade,id);
        return student;
}

    @RequestMapping("add")
    public String add(){
        return "teacherIndex";
    }

    /**
     * 跳转到登录页面
     */
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    /**
     * 登录逻辑处理
     * @param username 用户名
     * @param password 密码
     * @param role 权限等级
     * @return
     */
    @RequestMapping("login")
    public ModelAndView login(HttpServletRequest request, String username, String password, String role, Model model){
        return userService.login(request, username, password, role, model);
    }

    @RequestMapping("getClassAverageGrade")
    public void  getClassAverageGrade(String gradeTable,String classNumber){
        ClassGrade averageGrade = userService.getClassAverageGrade(gradeTable,classNumber);
        System.out.println(averageGrade.getBiologyAverageGrades());
        System.out.println(averageGrade.getChemistryAverageGrades());
    }

    /**
     * 注销
     * @return
     */
    @RequestMapping("signOut")
    public String signOut(){
        return "login";
    }

    /**
     * 教师端跳转到首页
     */
    @RequestMapping("goTeacherIndex")
    public String goIndex(){
        return "teacher/teacherIndex";
    }
}
