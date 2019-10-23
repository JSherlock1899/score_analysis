package com.slxy.analysis.student.Mapper;

import com.slxy.analysis.student.POJO.ClassRank;
import com.slxy.analysis.teacher.model.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;

@Mapper
public interface StudentGradeMapper {
    @Cacheable(cacheNames = "studentOneGrades")
    @Select("select name,chinese_grades,math_grades,english_grades,physics_grades,chemistry_grades,e.classNumber," +
            "biology_grades,history_grades,politics_grades,geography_grades,technology_grades,total_point_grades,r.id,r.classNumber,r.chinese_school_ranking,r.math_school_ranking," +
            "r.english_school_ranking,r.physics_school_ranking,r.chemistry_school_ranking," +
            "r.biology_school_ranking,r.history_school_ranking,r.politics_school_ranking," +
            "r.geography_school_ranking,r.technology_school_ranking,r.total_point_school_ranking," +
            "r.chinese_class_ranking,r.math_class_ranking,r.english_class_ranking,r.physics_class_ranking," +
            "r.chemistry_class_ranking,r.biology_class_ranking,r.history_class_ranking," +
            "r.politics_class_ranking,r.geography_class_ranking,r.technology_class_ranking,r.total_point_class_ranking," +
            "av.classNumber,av.chinese_average_grades,av.math_average_grades,av.english_average_grades," +
            "av.physics_average_grades,av.chemistry_average_grades,av.biology_average_grades," +
            "av.history_average_grades,av.politics_average_grades,av.geography_average_grades," +
            "av.technology_average_grades,av.total_point_average_grades from ${rank} r ,${average} av,${exam} e " +
            "join ${grade}_students_basic_info s on e.id = s.id  where s.classNumber = #{classNumber} and e.id=#{id}  and r.id=e.id and av.classNumber=e.classNumber")
     public ClassRank SearchOneGrade(@Param("id") String id, @Param("exam") String exam, @Param("rank") String rank,
                                     @Param("classNumber") String classNumber, @Param("grade") String grade, @Param("average") String average);

    @Cacheable(cacheNames = "studentbasic")
    @Select("SELECT id,password,name," +
            "sex, nation,id_card," +
            "   enter_time,telephone,native_place," +
            "homeAddress,school,picture," +
            "classNumber FROM ${grade}_students_basic_info s " +
            "WHERE s.id = #{id}")
    public Student SeachrByid(@Param("id") String id, @Param("grade") String grade);
}
