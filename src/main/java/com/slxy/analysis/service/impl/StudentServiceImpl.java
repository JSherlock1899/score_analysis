package com.slxy.analysis.service.impl;

import com.slxy.analysis.mapper.StudentMapper;
import com.slxy.analysis.model.Student;
import com.slxy.analysis.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/9/1 11:07
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentMapper studentMapper;

    @Override
    public Student getStudentById(String id) {
        return studentMapper.getStudentById(id);
    }
}
