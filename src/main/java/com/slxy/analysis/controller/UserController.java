package com.slxy.analysis.controller;

import com.slxy.analysis.mapper.StudentMapper;
import com.slxy.analysis.model.Student;
import com.slxy.analysis.result.CodeMsg;
import com.slxy.analysis.service.TeacherService;
import com.slxy.analysis.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    StudentMapper studentMapper;

    /**
     * 测试类
     * @param id 学生id
     * @return
     */
    @RequestMapping("test")
    @ResponseBody
    public Student test(String id){
        Student student = studentMapper.getStudentById(id);
        System.out.println(student.getId());
        return student;
    }

    @RequestMapping("add")
    @ResponseBody
    public String add(){
        return "add";
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
    public String login(String username, String password, String role, Model model){
        //获取subject
        Subject subject = SecurityUtils.getSubject();
        //封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username,password,role);
        //执行登录方法
        try {
            //
            subject.login(token);
            //登录成功
            System.out.println("登录成功");
            return "test";
        } catch (UnknownAccountException e) {
            //登录失败：用户名不存在
            model.addAttribute("msg", CodeMsg.USERNAME_ERROR.getMsg());
            return "login";
        }
        catch (IncorrectCredentialsException e) {
            //登录失败：密码错误
            model.addAttribute("msg", CodeMsg.PASSWORD_ERROR.getMsg());
            return "login";
        }
    }
}
