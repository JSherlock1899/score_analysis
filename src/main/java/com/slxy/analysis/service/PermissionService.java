package com.slxy.analysis.service;

import com.slxy.analysis.model.Permission;

import java.util.List;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/9/1 9:56
 */
public interface PermissionService {

    List<Permission> listTeacherPermissionsByRole(String role);
}
