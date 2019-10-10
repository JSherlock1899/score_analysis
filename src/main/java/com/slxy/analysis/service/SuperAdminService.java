package com.slxy.analysis.service;

import java.util.List;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/9/7 13:30
 */
public interface SuperAdminService {

    void createStudentTable(String tableName);

    void callAverageGrade(String subjects, String avgSubject, String examTable, String classGradeTable, String start_year );

    void callClassRanking(String gradeTable,String rankingTable,String subjects,String ranking,String classNumber);

    void callSchoolRanking(String gradeTable,String rankingTable,String subjects,String ranking,String classNumber);

    void callStudentRanking(String gradeTable, String rankingTable, List<String> classList);

    void callInitRankingTable(String gradeTable, String rankingTable);
}
