package com.slxy.analysis.service.impl;

import com.slxy.analysis.mapper.SuperAdminMapper;
import com.slxy.analysis.model.Student;
import com.slxy.analysis.service.SuperAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/9/7 13:30
 */
@Service
public class SuperAdminServiceImpl implements SuperAdminService {

    @Autowired
    SuperAdminMapper superAdminMapper;

    @Override
    public void createStudentTable(String tableName) {
        superAdminMapper.createStudentTable(tableName);
    }


    @Override
    public void callAverageGrade(String subjects, String avgSubject, String examTable, String classGradeTable, String start_year) {
        superAdminMapper.callAverageGrade(subjects,avgSubject,examTable,classGradeTable,start_year);
    }

    @Override
    public void callClassRanking(String gradeTable, String rankingTable, String subjects, String ranking, String classNumber) {
        superAdminMapper.callClassRanking(gradeTable,rankingTable,subjects,ranking,classNumber);
    }

    @Override
    public void callSchoolRanking(String gradeTable, String rankingTable, String subjects, String ranking, String classNumber) {
        superAdminMapper.callSchoolRanking(gradeTable,rankingTable,subjects,ranking,classNumber);
    }

    @Override
    public void callStudentRanking(String gradeTable, String rankingTable, List<String> classList){
        //循环计算并插入各班学生的排名信息
        for (String c : classList){
            for (int i = 0; i < Student.SUBJECT.length; i++ ){
                //学生成绩表中的科目字段名
                String subjects = Student.SUBJECT[i] + "_grades";
                //拼接字符串得到班级排名字段
                String classRanking = Student.SUBJECT[i] + "_class_ranking";
                //拼接字符串得到校排名字段
                String schoolRanking = Student.SUBJECT[i] + "_school_ranking";
                //调用存储过程计算并插入学生的班排名
                callClassRanking(gradeTable, rankingTable, subjects, classRanking,c);
                //调用存储过程计算并插入学生的校排名
                callSchoolRanking(gradeTable, rankingTable, subjects, schoolRanking,c);
            }
        }
    }

    /**
     * 初始化学生排名表
     * @param gradeTable 学生成绩表
     * @param rankingTable 学生排名表
     */
    @Override
    public void callInitRankingTable(String gradeTable, String rankingTable) {
        superAdminMapper.callInitRankingTable(gradeTable,rankingTable);
    }
}
