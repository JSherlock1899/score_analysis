package com.slxy.analysis.student.mapper;

import com.slxy.analysis.teacher.model.Exam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;

import java.util.concurrent.CopyOnWriteArrayList;

@Mapper
public interface ExamInfomation {

    /*
    根据年级获取考试信息
     */
    @Cacheable(cacheNames = "examinfomation")
    @Select("SELECT ex.exam_name,ex.exam_type,ex.exam_time,ex.exam_count,ex.table_name,ex.grade\n" +
            "FROM  exam_record AS ex WHERE ex.grade = #{year} order by exam_time desc ")
    public CopyOnWriteArrayList<Exam> getExaminfomation(@Param("year") String year);

    /**
     * 获取所有的考试列表
     * @return
     */
    @Select("select ex.exam_name,ex.exam_type,ex.exam_time,ex.exam_count,ex.table_name,ex.grade from exam_record ex")
    CopyOnWriteArrayList<Exam> getAllExamList();

}
