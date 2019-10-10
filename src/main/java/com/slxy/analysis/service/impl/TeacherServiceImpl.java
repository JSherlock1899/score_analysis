package com.slxy.analysis.service.impl;

import com.slxy.analysis.mapper.TeacherMapper;
import com.slxy.analysis.model.ClassGrade;
import com.slxy.analysis.model.Grade;
import com.slxy.analysis.model.Teacher;
import com.slxy.analysis.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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

    /**
     * 获取学生成绩列表
     * @param exam
     * @param classNumber
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<Grade> getStudentGrades(String exam, String classNumber,Integer pageNum, Integer pageSize,String subject,String sort) {
        //获取班级所处年级
        if (classNumber != null) {
            String grade = classNumber.substring(0, 2);
            subject = subject + "_grades";
            return teacherMapper.getStudentGrades(exam, classNumber, grade,pageNum,pageSize,subject,sort);
        }
        return null;
    }


    /**
     * 获取该教师所教的班级列表
     * @param request
     * @return
     */
    @Override
    public List<String> listClass(HttpServletRequest request){
        //从session中获取username
        String username = (String) request.getSession().getAttribute("username");
        //获取教师所教的课程
        Teacher teacher = getTeacherNameAndSubject(username);
        //根据教师所教的课程和工号获取教师所教的班级
        List<String> classList = getTeacherClass(teacher.getSubject(), username);
        return classList;
    }

    /**
     *获取各班级的成绩
     */
    @Override
    @Cacheable("ClassGrades")
    public List<ClassGrade> getClassGrades(String classGradeTable, String startYear, Integer pageNum, Integer pageSize,String subject, String sort) {
        subject = subject + "_average_grades";
        return teacherMapper.getClassGrades(classGradeTable, startYear, pageNum, pageSize, subject, sort);
    }

    /**
     * 获取某个年级有哪几个班
     * @param startYear 入学年份，也可以看做是年级
     * @return
     */
    @Override
    public List<String> getGradeClass(String startYear) {
        return teacherMapper.getGradeClass(startYear);
    }

    /**
     * 动态查询教师所教的年级（一个教师可能教多个年级）
     * @param classes 教师所教的班级列表
     * @return
     */
    @Override
    public List<String> getTeacherGrade(List<String> classes) {
        String s;
        List<String> grade = new ArrayList<String>();
        for (String c : classes){
            //分割班级字符串，得到前两位数，也就是对应的年级
            s = c.substring(0, 2);
            if (!grade.contains(s)){
                grade.add(s);
            }
        }
        return grade;
    }


}
