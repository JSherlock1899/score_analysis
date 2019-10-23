package com.slxy.analysis.teacher.service;

import com.slxy.analysis.teacher.model.Student;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/9/1 11:05
 */
public interface StudentService {

    Student getStudentPassword(Integer grade, String id);

    Student getStudentNameAndClassNumber(Integer grade, String id);
}
