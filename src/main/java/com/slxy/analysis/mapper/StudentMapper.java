package com.slxy.analysis.mapper;

import com.slxy.analysis.model.Student;
import org.apache.ibatis.annotations.Select;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/9/1 11:04
 */
public interface StudentMapper {

    /**
     * 根据id获取学生信息，id同时也是用户名
     * 在id上建立了唯一索引
     * @param id 用户名
     * @return
     */
    @Select("select * from 17_students_basic_info where id = #{id}")
    Student getStudentById(String id);

}
