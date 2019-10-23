package com.slxy.analysis.student.Mapper;

import com.slxy.analysis.teacher.model.Exam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;

import java.util.concurrent.CopyOnWriteArrayList;

@Mapper
public interface ExamInfomation {

    @Cacheable(cacheNames = "examinfomation")
    @Select("SELECT ex.exam_name,ex.exam_type,ex.exam_time,ex.exam_count,ex.table_name,ex.grade\n" +
            "FROM  exam_record AS ex WHERE ex.grade = #{year}")
    public CopyOnWriteArrayList<Exam> getExaminfomation(@Param("year") String year);
}
