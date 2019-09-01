package com.slxy.analysis.service.impl;

import com.slxy.analysis.mapper.PermissionMapper;
import com.slxy.analysis.model.Permission;
import com.slxy.analysis.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/9/1 10:16
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionMapper permissionMapper;

    @Override
    public List<Permission> listTeacherPermissionsByRole(String role) {
        return permissionMapper.listTeacherPermissionsByRole(role);
    }
}
