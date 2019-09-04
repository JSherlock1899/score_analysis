package com.slxy.analysis.controller;

import com.slxy.analysis.model.Student;
import com.slxy.analysis.model.Teacher;
import com.slxy.analysis.model.User;
import com.slxy.analysis.result.CodeMsg;
import com.slxy.analysis.service.StudentService;
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
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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
    public ModelAndView login(String username, String password, String role, Model model){
        ModelAndView mv = new ModelAndView();
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
            //判断当前用户是学生还是教师
            if (User.STUDENT.equals(role)){
                //初始化学生用户信息
                //获取其所在年级
                Integer grade = Integer.valueOf(token.getUsername().substring(0,2));
                //根据学生用户名获取其姓名和所在的班级
                Student student = studentService.getStudentNameAndClassNumber(grade, username);
                if (student != null) {
                    mv.addObject("name", student.getName());
                    mv.addObject("classNumber", student.getClassNumber());
                }
            }else{
                //初始化教师用户的信息
                //根据教师用户名获取其初始化信息
                Teacher teacher = teacherService.getTeacherNameAndSubject(username);
                mv.addObject("name", teacher.getName());
                //获取教师所教的课程
                List<String> teacherClass = teacherService.getTeacherClass(teacher.getSubject(), username);
                for (String s:teacherClass) {
                    System.out.println("s = " + s);
                }

            }
            mv.setViewName("test");
        } catch (UnknownAccountException e) {
            //登录失败：用户名不存在
            model.addAttribute("msg", CodeMsg.USERNAME_ERROR.getMsg());
            mv.setViewName("login");
        }
        catch (IncorrectCredentialsException e) {
            //登录失败：密码错误
            model.addAttribute("msg", CodeMsg.PASSWORD_ERROR.getMsg());
            mv.setViewName("login");
        }
        return mv;
    }
}
