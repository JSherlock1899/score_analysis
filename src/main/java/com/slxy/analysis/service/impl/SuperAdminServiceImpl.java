package com.slxy.analysis.service.impl;

import com.slxy.analysis.mapper.SuperAdminMapper;
import com.slxy.analysis.service.SuperAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/9/7 13:30
 */
@Service
public class SuperAdminServiceImpl implements SuperAdminService {

    @Autowired
    SuperAdminMapper superAdminMapper;
    @Override
    public void createStudentTable(String tableName) {
        superAdminMapper.createStudentTable(tableName);
    }
}
