package com.slxy.analysis.mapper;

import com.slxy.analysis.model.ClassGrade;
import com.slxy.analysis.model.Exam;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/8/31 17:11
 */

public interface UserMapper {

    /**
     * 获取考试名
     * @return
     */
    @Cacheable(cacheNames = "exam")
    @Select("select exam_name,table_name from exam_record where grade = ${grade}")
    List<Exam> getExam(String grade);

    /**
     * 获取某个班的各科平均分
     * @param gradeTable 成绩表
     * @param classNumber 班级
     * @return
     */
    @Select("SELECT chinese_average_grades, math_average_grades, english_average_grades, physics_average_grades, chemistry_average_grades, biology_average_grades, history_average_grades, politics_average_grades, geography_average_grades, technology_average_grades, total_point_average_grades FROM ${gradeTable} where classNumber = #{classNumber}")
    ClassGrade getClassAverageGrade(String gradeTable, String classNumber);


}
