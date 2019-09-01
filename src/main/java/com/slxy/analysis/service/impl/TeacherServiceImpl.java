package com.slxy.analysis.service.impl;

import com.slxy.analysis.mapper.TeacherMapper;
import com.slxy.analysis.model.Teacher;
import com.slxy.analysis.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Teacher getTeacherById(String id) {
        return teacherMapper.getTeacherById(id);
    }

    @Override
    public String getTeacherRole(String id) {
        return teacherMapper.getTeacherRole(id);
    }
}
