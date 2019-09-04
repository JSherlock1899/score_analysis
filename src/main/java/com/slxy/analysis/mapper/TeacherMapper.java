package com.slxy.analysis.mapper;

import com.slxy.analysis.model.Teacher;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/9/1 11:05
 */
public interface TeacherMapper {

    /**
     * 根据id获取教师信息，id同时也是用户名
     * 在id上建立了唯一索引
     * @param id 教师工号
     * @return
     */
    @Select("select * from teacher_basic_info where id = #{id}")
    Teacher getTeacherById(String id);

    /**
     * 查询教师的权限角色
     * @param id 用户名
     * @return 教师工号
     */
    @Select("select role from teacher_basic_info where id = #{id}")
    String getTeacherRole(String id);

    /**
     * 查询教师的姓名与所教课程
     * @param id  用户名
     * @return 教师工号
     */
    @Select("select name,subject from teacher_basic_info where id = #{id}")
    Teacher getTeacherNameAndSubject(String id);

    /**
     * 获取教师所教的班级的集合
     * @return
     */
    @Select("select class_number from class_teacher_info where ${subject} = #{id}")
    List<String> getTeacherClass(String subject,String id);
}
