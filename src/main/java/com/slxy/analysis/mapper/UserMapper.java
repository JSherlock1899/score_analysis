package com.slxy.analysis.mapper;

import com.slxy.analysis.model.Exam;
import org.apache.ibatis.annotations.Select;

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
    @Select("select exam_name,table_name from exam_record")
    List<Exam> getExam();
}
