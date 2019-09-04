package com.slxy.analysis.service.impl;

import com.slxy.analysis.mapper.StudentMapper;
import com.slxy.analysis.model.Student;
import com.slxy.analysis.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(cacheNames = "student")
    public Student getStudentPassword(Integer grade, String id) {
        return studentMapper.getStudentPassword(grade, id);
    }

    @Override
    @Cacheable(cacheNames = "StudentNameAndClass")
    public Student getStudentNameAndClassNumber(Integer grade, String id) {
        return studentMapper.getStudentNameAndClassNumber(grade, id);
    }
}
