package com.slxy.analysis.student.service;

import com.slxy.analysis.teacher.model.Exam;
import com.slxy.analysis.teacher.model.Student;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CopyOnWriteArrayList;

public interface StService {

    /*
    查询某一个学生的成绩，根据id
     */
    public ModelAndView SearchOne(String exam, HttpServletRequest request, ModelAndView mv);

    /*
    根据id查询用户基本信息
     */
    public Student SeacheStudentBasic(HttpServletRequest request);
    /*
    获取考试信息
     */
    public CopyOnWriteArrayList<Exam> getExaminfomation(String id);
}
