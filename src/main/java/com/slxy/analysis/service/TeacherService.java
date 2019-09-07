package com.slxy.analysis.service;

import com.slxy.analysis.model.Grade;
import com.slxy.analysis.model.Teacher;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/9/1 11:06
 */
public interface TeacherService {

    Teacher getTeacherById(String id);

    String getTeacherRole(String id);

    Teacher getTeacherNameAndSubject(String id);

    List<String> getTeacherClass(String subject, String id);

    List<Grade> getStudentGrades(String exam, String classNumber);

    public ModelAndView listClass(HttpServletRequest request);
}