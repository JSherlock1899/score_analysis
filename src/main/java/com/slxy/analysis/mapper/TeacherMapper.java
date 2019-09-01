package com.slxy.analysis.mapper;

import com.slxy.analysis.model.Teacher;
import org.apache.ibatis.annotations.Select;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/9/1 11:05
 */
public interface TeacherMapper {

    /**
     * 根据id获取教师信息，id同时也是用户名
     * 在id上建立了唯一索引
     * @param id 用户名
     * @return
     */
    @Select("select * from teacher_basic_info where id = #{id}")
    Teacher getTeacherById(String id);

    /**
     * 查询教师的权限角色
     * @param id 用户名
     * @return
     */
    @Select("select role from teacher_basic_info where id = #{id}")
    String getTeacherRole(String id);
}
