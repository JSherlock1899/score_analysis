package com.slxy.analysis.service.impl;

import com.slxy.analysis.mapper.TeacherMapper;
import com.slxy.analysis.model.Teacher;
import com.slxy.analysis.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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
}
