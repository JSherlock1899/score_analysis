package com.slxy.analysis.teacher.mapper;

import com.slxy.analysis.teacher.model.ClassGrade;
import com.slxy.analysis.teacher.model.Exam;
import com.slxy.analysis.teacher.model.Grade;
import com.slxy.analysis.teacher.model.Teacher;
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
            "biology_grades,history_grades,politics_grades,geography_grades,technology_grades,total_point_grades from ${exam} e " +
            "join ${grade}_students_basic_info s on e.id = s.id  where s.classNumber = #{classNumber} order by ${subject} ${sort}")
    List<Grade> getStudentGrades(String exam, String classNumber, String grade,Integer pageNum, Integer pageSize,String subject,String sort);

    /**
     * 根据年级和班级成绩表查出各班的平均成绩
     * @param classGradeTable 该次考试各班的平均成绩
     * @param startYear 年级
     * @param pageNum 当前页
     * @param pageSize 每页显示数据的条数
     * @return
     */
    @Select("select e.classNumber,chinese_average_grades,math_average_grades,english_average_grades,physics_average_grades,chemistry_average_grades," +
            "biology_average_grades,history_average_grades,politics_average_grades,geography_average_grades,technology_average_grades,total_point_average_grades from ${classGradeTable} e " +
            "join class_basic_info c on e.classNumber = c.classNumber where c.start_year = #{startYear} order by ${subject} ${sort}")
    List<ClassGrade> getClassesGrades(String classGradeTable, String startYear, Integer pageNum, Integer pageSize,String subject, String sort);

    /**
     * 获取某个年级有哪几个班
     * @param startYear 入学时间，也可以看做是年级
     * @return
     */
    @Select("select classNumber from class_basic_info where start_year = #{startYear}")
    List<String> getGradeClass(String startYear);

    /**
     * 获取每个年级的考试列表
     * @param grade
     * @return
     */
    @Select("select exam_name,table_name from exam_record where grade = #{grade}")
    List<Exam> getExamList(String grade);

    /**
     * 获取某班各次考试的平均分
     * @param examTable
     * @param classNumber
     * @return
     */
    @Select("select chinese_average_grades,math_average_grades,english_average_grades,physics_average_grades,chemistry_average_grades," +
            "biology_average_grades,history_average_grades,politics_average_grades,geography_average_grades,technology_average_grades," +
            "total_point_average_grades from ${examTable} where classNumber = #{classNumber}")
    Grade getClassGrades(String examTable, String classNumber);

    /**
     * 查询各班单科平均分
     * @param subject 科目
     * @param examTable 班级成绩表
     * @return
     */
    @Select("select ${subject} from ${examTable} where classNumber = #{classNumber}")
    List<ClassGrade> selectSingleSubjectGrades(String subject, String examTable, String classNumber);
    /**
     * 根据考试表获取考试名
     * @param examTable
     * @return
     */
    @Select("select exam_name from exam_record where table_name = #{examTable}")
    String getExamName(String examTable);

    /**
     * 查询某班某次考试全校前num名的人数
     * @param rankingTable
     * @param classNumber
     * @param num
     * @return
     */
    @Select("select count(*) from ${rankingTable} where total_point_school_ranking <= ${num} and classNumber = #{classNumber}")
    Integer getTopStudnetNumber(String rankingTable, String classNumber, Integer num);

    /**
     * 查询某班学生人数
     * @param classNumber
     * @return
     */
    @Select("select class_count from class_basic_info where classNumber = #{classNumber}")
    Integer getClassStudentNumber(String classNumber);

    /**
     * 查询教师备注信息
     * @param id 教师工号
     * @return
     */
    @Select("select remark from teacher_basic_info where id = #{id}")
    String getTeacherRemark(String id);

    /**
     * 查询当前有哪几个年级
     * @return
     */
    @Select("select grade from grade_basic_info where status = '1'")
    List<String> getPresentGrade();

    /**
     * 查询某班过线人数
     * @param classNumber 班级
     * @param examTable 考试表
     * @param cutOffGrade 分数线
     * @return
     */
    @Select("select count(*) from ${examTable} where classNumber = #{classNumber} and total_point_grades >= #{cutOffGrade}")
   Integer selectPassLineStudents(String classNumber, String examTable, Integer cutOffGrade);


    /**
     * 查询教师所教的课程
     * @param id 教师工号
     * @return
     */
    @Select("select subject from teacher_basic_info where id = #{id}")
    String selectTeacherSubject(String id);

    /**
     * 查询某个年级最近考试名和考试表的名称
     * @param grade
     * @return
     */
    @Select("select exam_name,exam_time,table_name from exam_record where grade = #{grade}")
    List<Exam> selectRecentlyExams(String grade);

    @Select("select count(*) from ${examTable} where total_point_grades >= #{cutOffGrade}")
    Integer calcPassLineCount(String examTable, Integer cutOffGrade);
}
