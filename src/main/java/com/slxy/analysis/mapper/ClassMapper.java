package com.slxy.analysis.mapper;

import com.slxy.analysis.model.Grade;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/9/8 14:43
 */
public interface ClassMapper {

    @Select("select name,chinese_grades,math_grades,english_grades,physics_grades,chemistry_grades," +
            "biology_grades,history_grades,politics_grades,geography_grades,technology_grades from ${exam} ")
    List<Grade> getClassGrades(String exam, String grade, Integer pageNum, Integer pageSize);
}
