package com.slxy.analysis.teacher.mapper;

import com.slxy.analysis.teacher.model.Exam;
import com.slxy.analysis.teacher.model.Teacher;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/9/7 13:21
 */

public interface SuperAdminMapper {

    /**
     * 创建学生成绩表
     * @param examTable 表名
     */
    @Select("CREATE TABLE ${examTable} (\n" +
            "  `id` int(11) NOT NULL,\n" +
            "  `classNumber` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
            "  `chinese_grades` char(8) DEFAULT NULL,\n" +
            "  `math_grades` char(8) DEFAULT NULL,\n" +
            "  `english_grades` char(8) DEFAULT NULL,\n" +
            "  `physics_grades` char(8) DEFAULT NULL,\n" +
            "  `chemistry_grades` char(8) DEFAULT NULL,\n" +
            "  `biology_grades` char(8) DEFAULT NULL,\n" +
            "  `history_grades` char(8) DEFAULT NULL,\n" +
            "  `politics_grades` char(8) DEFAULT NULL,\n" +
            "  `geography_grades` char(8) DEFAULT NULL,\n" +
            "  `technology_grades` char(8) DEFAULT NULL,\n" +
            "  `total_point_grades` char(8) DEFAULT NULL,\n" +
            "  PRIMARY KEY (`id`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8;")
    void createStudentsGradesTable(String examTable);

    /**
     * 创建学生排名表
     * @param examTable 表名
     */
    @Select("CREATE TABLE ${examTable} (\n" +
            "  `id` int(11) NOT NULL,\n" +
            "  `classNumber` varchar(8) DEFAULT NULL,\n" +
            "  `chinese_school_ranking` int(8) DEFAULT NULL,\n" +
            "  `math_school_ranking` int(8) DEFAULT NULL,\n" +
            "  `english_school_ranking` int(8) DEFAULT NULL,\n" +
            "  `physics_school_ranking` int(8) DEFAULT NULL,\n" +
            "  `chemistry_school_ranking` int(8) DEFAULT NULL,\n" +
            "  `biology_school_ranking` int(8) DEFAULT NULL,\n" +
            "  `history_school_ranking` int(8) DEFAULT NULL,\n" +
            "  `politics_school_ranking` int(8) DEFAULT NULL,\n" +
            "  `geography_school_ranking` int(8) DEFAULT NULL,\n" +
            "  `technology_school_ranking` int(8) DEFAULT NULL,\n" +
            "  `total_point_school_ranking` int(8) DEFAULT NULL,\n" +
            "  `chinese_class_ranking` int(8) DEFAULT NULL,\n" +
            "  `math_class_ranking` int(8) DEFAULT NULL,\n" +
            "  `english_class_ranking` int(8) DEFAULT NULL,\n" +
            "  `physics_class_ranking` int(8) DEFAULT NULL,\n" +
            "  `chemistry_class_ranking` int(8) DEFAULT NULL,\n" +
            "  `biology_class_ranking` int(8) DEFAULT NULL,\n" +
            "  `history_class_ranking` int(8) DEFAULT NULL,\n" +
            "  `politics_class_ranking` int(8) DEFAULT NULL,\n" +
            "  `geography_class_ranking` int(8) DEFAULT NULL,\n" +
            "  `technology_class_ranking` int(8) DEFAULT NULL,\n" +
            "  `total_point_class_ranking` int(8) DEFAULT NULL,\n" +
            "  PRIMARY KEY (`id`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8;")
    void createStudentsRankingTable(String examTable);

    /**
     * 创建班级成绩表
     * @param examTable
     */
    @Select("CREATE TABLE ${examTable} (\n" +
            "  `classNumber` varchar(8) NOT NULL,\n" +
            "  `chinese_average_grades` char(8) DEFAULT NULL,\n" +
            "  `math_average_grades` char(8) DEFAULT NULL,\n" +
            "  `english_average_grades` char(8) DEFAULT NULL,\n" +
            "  `physics_average_grades` char(8) DEFAULT NULL,\n" +
            "  `chemistry_average_grades` char(8) DEFAULT NULL,\n" +
            "  `biology_average_grades` char(8) DEFAULT NULL,\n" +
            "  `history_average_grades` char(8) DEFAULT NULL,\n" +
            "  `politics_average_grades` char(8) DEFAULT NULL,\n" +
            "  `geography_average_grades` char(8) DEFAULT NULL,\n" +
            "  `technology_average_grades` char(8) DEFAULT NULL,\n" +
            "  `total_point_average_grades` char(8) DEFAULT NULL,\n" +
            "  PRIMARY KEY (`classNumber`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8;")
    void createClassGrades(String examTable);

    /**
     * 在考试记录表中添加对应的记录
     * @param examTable 该次考试对应的数据表名
     * @param grade 年级
     * @param examTime 考试时间
     * @param examName 考试名
     */
    @Insert("insert into exam_record (exam_name,exam_time,table_name,grade) values(#{examName},#{examTime},#{examTable},#{grade})")
    void createExamRecord(String examTable, String grade, String examTime, String examName);

    /**
     * 调用存储过程计算各班平均分并插入对应表
     * @param subjects 考试科目字段
     * @param avgSubject 考试平均分字段
     * @param examTable 考试表
     * @param classGradeTable 班级成绩表
     * @param start_year 入学年份（即年级）
     */
    @Select("CALL insertClassGrade('${subjects}','${avgSubject}','${examTable}','${classGradeTable}','${start_year}');")
    void callAverageGrade(String subjects, String avgSubject, String examTable, String classGradeTable, String start_year );

    /**
     *
     * @param gradeTable 学生成绩表
     * @param rankingTable 学生排名表
     * @param subjects 考试科目字段
     * @param ranking 学生排名字段
     * @param classNumber 班级
     */
    @Select("CALL school_ranking('${gradeTable}','${rankingTable}','${subjects}','${ranking}','${classNumber}');")
    void callSchoolRanking(String gradeTable,String rankingTable,String subjects,String ranking,String classNumber);

    /**
     *
     * @param gradeTable 学生成绩表
     * @param rankingTable 学生排名表
     * @param subjects 考试科目字段
     * @param ranking 学生排名字段
     * @param classNumber 班级
     */
    @Select("CALL class_ranking('${gradeTable}','${rankingTable}','${subjects}','${ranking}','${classNumber}');")
    void callClassRanking(String gradeTable,String rankingTable,String subjects,String ranking,String classNumber);

    /**
     * 初始化学生排名表
     * @param gradeTable 学生成绩表
     * @param rankingTable 学生排名表
     */
    @Select("CALL initRankingTable('${gradeTable}','${rankingTable}')")
    void callInitRankingTable(String gradeTable, String rankingTable);

    /**
     * 查询所有考试信息
     * @return
     */
    @Select("select exam_name,exam_time,grade from exam_record")
    List<Exam> selectAllExam();

    /**
     * 根据教师的权限角色查询教师信息，获取各个权限角色的教师列表
     * @param role
     * @return
     */
    @Select("select id,name,sex,nation,id_card,telephone,native_place,homeAddress,job_grade,pol_stat," +
            "employed_time,graduate_school,picture,subject,remark from teacher_basic_info where role = #{role}")
    List<Teacher> selectTeacherListByRole(String role);

    /**
     * 根据教师名查询教师（模糊查询）
     * @param name 教师名
     * @return
     */
    @Select("select id,name,sex,nation,id_card,telephone,native_place,homeAddress,job_grade,pol_stat," +
            "employed_time,graduate_school,picture,subject,remark,role from teacher_basic_info where name like '%${name}%'")
    List<Teacher> selectTeacherListByName(String name);

    /**
     * 修改教师权限
     * @param role 权限角色
     * @param id 工号
     * @return
     */
    @Update("update teacher_basic_info set role = #{role} where id = #{id}")
    int updateTeacherAuthority(String role, String id);

    /**
     * 根据教师id修改其个人信息
     * @param id
     * @param name
     * @param sex
     * @param nation
     * @param id_card
     * @param telephone
     * @param native_place
     * @param homeAddress
     * @param job_grade
     * @param pol_stat
     * @param graduate_school
     * @param subject
     * @return
     */
    @Update("update teacher_basic_info set name = #{name},sex = #{sex},id_card = #{id_card},telephone = #{telephone},native_place = #{native_place},homeAddress = #{homeAddress},job_grade = #{job_grade},pol_stat = #{pol_stat},graduate_school = #{graduate_school},subject = #{subject} where id = #{id} ")
    int updateTerInfo(String id, String name, String sex, String nation,String id_card,String telephone,String native_place,String homeAddress,String job_grade,String pol_stat,String graduate_school,String subject);

    @Delete("delete from teacher_basic_info where id = #{id}")
    void delTer(String id);
}
