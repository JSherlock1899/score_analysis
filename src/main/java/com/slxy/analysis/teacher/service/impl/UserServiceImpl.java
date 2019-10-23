package com.slxy.analysis.teacher.service.impl;

import com.slxy.analysis.teacher.mapper.UserMapper;
import com.slxy.analysis.teacher.model.*;
import com.slxy.analysis.teacher.result.CodeMsg;
import com.slxy.analysis.teacher.service.StudentService;
import com.slxy.analysis.teacher.service.TeacherService;
import com.slxy.analysis.teacher.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/8/31 17:19
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    TeacherService teacherService;
    @Autowired
    StudentService studentService;

    @Override
    public ModelAndView login(HttpServletRequest request, String username, String password, String role, Model model) {
        ModelAndView mv = new ModelAndView();
        //将username存到session中
        request.getSession().setAttribute("username", username);
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
                mv.setViewName("student/studentIndex");
            }else{
                //初始化教师用户的信息
                //根据教师用户名获取其初始化信息
                Teacher teacher = teacherService.getTeacherNameAndSubject(username);
                mv.addObject("name", teacher.getName());
                mv.setViewName("teacher/teacherIndex");
            }
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

    /**
     * 获取考试名
     * @return
     */
    @Override
    public HashSet<Exam> getExam(List<String> grades) {
        List<Exam> exams;
        HashSet<Exam> examSet = new HashSet<Exam>();
        for (String grade : grades){
            grade = grade.substring(0, 2);
            exams = userMapper.getExam(grade);
            if (exams != null){
                for (Exam e : exams){
                    if (!examSet.contains(e)){
                        examSet.add(e);
                    }
                }
            }
        }
        return examSet;
    }

    /**
     * 获取某个班的各科平均分
     * @param gradeTable 成绩表
     * @param classNumber 班级
     * @return
     */
    @Override
    @Cacheable(cacheNames = "ClassAverageGrade")
    public ClassGrade getClassAverageGrade(String gradeTable,String classNumber) {
        //拼接字符串获得班级成绩表名
        String classGradeTable = gradeTable.replace("students","class");
        return userMapper.getClassAverageGrade(classGradeTable, classNumber);
    }


}
