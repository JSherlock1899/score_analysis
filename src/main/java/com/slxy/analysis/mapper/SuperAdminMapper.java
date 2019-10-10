package com.slxy.analysis.mapper;

import org.apache.ibatis.annotations.Select;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/9/7 13:21
 */

public interface SuperAdminMapper {

    @Select("CREATE TABLE ${tableName} (\n" +
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
            "  `total_point_class_ranking` char(8) DEFAULT NULL,\n" +
            "  `total_point_ranking` char(8) DEFAULT NULL,\n" +
            "  PRIMARY KEY (`id`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8;")
    void createStudentTable(String tableName);

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
}
