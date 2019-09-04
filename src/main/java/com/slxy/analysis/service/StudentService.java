package com.slxy.analysis.service;

import com.slxy.analysis.model.Student;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/9/1 11:05
 */
public interface StudentService {

    Student getStudentPassword(Integer grade, String id);

    Student getStudentNameAndClassNumber(Integer grade, String id);
}
