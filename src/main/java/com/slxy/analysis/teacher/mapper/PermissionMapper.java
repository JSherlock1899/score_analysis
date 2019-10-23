package com.slxy.analysis.teacher.mapper;

import com.slxy.analysis.teacher.model.Permission;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/9/1 1:05
 */
public interface PermissionMapper {

    @Select("select permission from permission p join role_permission_ref r on p.id =  r.permission_id where r.role_id = #{role}")
    List<Permission> listTeacherPermissionsByRole(String role);
}
