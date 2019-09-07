package com.slxy.analysis.mapper;

import com.slxy.analysis.model.Grade;
import com.slxy.analysis.model.Teacher;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/9/1 11:05
 */
public interface TeacherMapper extends UserMapper {

    /**
     * 根据id获取教师信息，id同时也是用户名
     * 在id上建立了唯一索引
     * @param id 教师工号
     * @return 教师基本信息
     */
    @Select("select * from teacher_basic_info where id = #{id}")
    Teacher getTeacherById(String id);

    /**
     * 查询教师的权限角色
     * @param id 教师工号
     * @return 教师的权限角色
     */
    @Cacheable(cacheNames = "teacherRole")
    @Select("select role from teacher_basic_info where id = #{id}")
    String getTeacherRole(String id);

    /**
     * 查询教师的姓名与所教课程
     * @param id  教师工号
     * @return
     */
    @Cacheable(cacheNames = "teacherNameAndSubject")
    @Select("select name,subject from teacher_basic_info where id = #{id}")
    Teacher getTeacherNameAndSubject(String id);

    /**
     * 获取教师所教的班级的集合
     * @param subject 教师所教的课程
     * @param id 教师工号
     * @return 教师所教的班级集合
     */
    @Cacheable(cacheNames = "teacherClass")
    @Select("select class_number from class_teacher_info where ${subject} = #{id}")
    List<String> getTeacherClass(String subject, String id);

    /**
     * 根据考试名称和班级获取学生成绩的集合
     * @param exam 考试名称
     * @param classNumber 班级
     * @param grade 年级
     * @return 学生成绩的集合
     */
    @Cacheable(cacheNames = "studentGrades")
    @Select("select name,chinese_grades,math_grades,english_grades,physics_grades,chemistry_grades," +
            "biology_grades,history_grades,politics_grades,geography_grades,technology_grades from ${exam} e " +
            "join ${grade}_students_basic_info s on e.id = s.id where s.classNumber = #{classNumber}")
    List<Grade> getStudentGrades(String exam, String classNumber, String grade);

}
