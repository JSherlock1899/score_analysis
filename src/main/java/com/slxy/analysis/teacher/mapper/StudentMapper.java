package com.slxy.analysis.teacher.mapper;

import com.slxy.analysis.teacher.model.Student;
import org.apache.ibatis.annotations.Select;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/9/1 11:04
 */
public interface StudentMapper extends UserMapper {

    /**
     * 根据id和所在年级获取学生密码，id同时也是用户名
     * 在id上建立了唯一索引
     * @param  grade 年纪
     * @param id 用户名
     * @return
     */
    @Select("select password from #{grade}_students_basic_info where id = #{id}")
    Student getStudentPassword(Integer grade, String id);

    /**
     * 根据id和所在年级获取学生姓名和班级，id同时也是用户名
     * @param grade 年级
     * @param id 用户名
     * @return
     */
    @Select("select name,classNumber from #{grade}_students_basic_info where id = #{id}")
    Student getStudentNameAndClassNumber(Integer grade, String id);

}
