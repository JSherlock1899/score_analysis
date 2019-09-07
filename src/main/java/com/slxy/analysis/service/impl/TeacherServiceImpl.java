package com.slxy.analysis.service.impl;

import com.slxy.analysis.mapper.TeacherMapper;
import com.slxy.analysis.model.Grade;
import com.slxy.analysis.model.Teacher;
import com.slxy.analysis.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/9/1 11:06
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    TeacherMapper teacherMapper;

    @Override
    @Cacheable(cacheNames = "teacher")
    public Teacher getTeacherById(String id) {
        return teacherMapper.getTeacherById(id);
    }

    @Override
    @Cacheable(cacheNames = "teacherRole")
    public String getTeacherRole(String id) {
        return teacherMapper.getTeacherRole(id);
    }

    @Override
    @Cacheable(cacheNames = "teacherNameAndSubject")
    public Teacher getTeacherNameAndSubject(String id) {
        return teacherMapper.getTeacherNameAndSubject(id);
    }

    @Override
    @Cacheable(cacheNames = "teacherClass")
    public List<String> getTeacherClass(String subject, String id){
        return teacherMapper.getTeacherClass(subject, id);
    }

    @Override
    public List<Grade> getStudentGrades(String exam, String classNumber) {
        //获取班级所处年级
        if (classNumber != null) {
            String grade = classNumber.substring(0, 2);
            return teacherMapper.getStudentGrades(exam, classNumber, grade);
        }
        return null;
    }


    public ModelAndView listClass(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        //从session中获取username
        String username = (String) request.getSession().getAttribute("username");
        //获取教师所教的课程
        Teacher teacher = getTeacherNameAndSubject(username);
        //根据教师所教的课程和工号获取教师所教的班级
        List<String> classList = getTeacherClass(teacher.getSubject(), username);
        mv.addObject("classList", classList);
        return mv;
    }




}
