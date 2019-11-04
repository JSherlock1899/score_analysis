package com.slxy.analysis.teacher.service;

import com.slxy.analysis.teacher.model.Exam;
import com.slxy.analysis.teacher.model.Teacher;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/9/7 13:30
 */
public interface SuperAdminService {

    void createStudentsGradesTable(String examTable);

    void callAverageGrade(String subjects, String avgSubject, String examTable, String classGradeTable, String start_year );

    void callClassRanking(String gradeTable,String rankingTable,String subjects,String ranking,String classNumber);

    void callSchoolRanking(String gradeTable,String rankingTable,String subjects,String ranking,String classNumber);

    void callStudentRanking(String gradeTable, String rankingTable, List<String> classList);

    void callInitRankingTable(String gradeTable, String rankingTable);

    void createExamTable(String grade, String examTime);

    void createExamRecord(String grade, String examTime, String examName);

    void createStudentsRankingTable(String examTable);

    void createClassGrades(String examTable);

    void doBackups();

    List<Exam> selectAllExam();

    List<Teacher> selectTeacherListByRole(String role);

    ModelAndView selectTeacherListByName(String name);

    ModelAndView updateTeacherAuthority(String role, String id);

    /*
  将当前excel表上传至服务器
   */
    public boolean uploadfile(HttpServletRequest req);

    int updateTerInfo(Teacher teacher);

    void delTer(String id);

}
