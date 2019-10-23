package com.slxy.analysis.teacher.service.impl;

import com.slxy.analysis.teacher.mapper.PermissionMapper;
import com.slxy.analysis.teacher.model.Permission;
import com.slxy.analysis.teacher.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(cacheNames = "userPermissions")
    public List<Permission> listTeacherPermissionsByRole(String role) {
        return permissionMapper.listTeacherPermissionsByRole(role);
    }
}
