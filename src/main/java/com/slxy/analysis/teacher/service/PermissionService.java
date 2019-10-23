package com.slxy.analysis.teacher.service;

import com.slxy.analysis.teacher.model.Permission;

import java.util.List;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/9/1 9:56
 */
public interface PermissionService {

    List<Permission> listTeacherPermissionsByRole(String role);
}
