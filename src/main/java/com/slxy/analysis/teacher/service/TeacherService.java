package com.slxy.analysis.teacher.service;

import com.slxy.analysis.teacher.model.ClassGrade;
import com.slxy.analysis.teacher.model.Exam;
import com.slxy.analysis.teacher.model.Grade;
import com.slxy.analysis.teacher.model.Teacher;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/9/1 11:06
 */
public interface TeacherService {

    Teacher getTeacherById(String id);

    String getTeacherRole(String id);

    Teacher getTeacherNameAndSubject(String id);

    List<String> getTeacherClass(String subject, String id);

    List<Grade> getStudentGrades(String exam, String classNumber, Integer pageNum, Integer pageSize,String subject,String sort);

    List<String> listClass(HttpServletRequest request);

    List<ClassGrade> getClassesGrades(String classGradeTable, String startYear, Integer pageNum, Integer pageSize, String subject, String sort);

    List<String> getGradeClass(String startYear);

    List<String> getTeacherGrade(List<String> classes);

    List<Exam> getExamList(String grade);

    Grade getClassGrades(String examTable, String classNumber);

    List<Grade> getClassGradeList(String classNumber);

    String getExamName(String examTable);

    Integer getTopStudnetNumber(String examTable, String classNumber, Integer num);

    Integer getClassStudentNumber(String classNumber);

    List<Map<String, String>> getStudnetRankingDistribute(String examTable, String classNumber);

    String getTeacherRemark(String id);

    List<String> getPresentGrade();

    List<String> getClassList(HttpServletRequest request, String role, String remark, String adminClass, String startYear);

    List<Map<String, Integer>> selectPassLineStudents(String startYear, String exam, String cutOffGrade);

    String selectTeacherSubject(String id);

    List disposeList(List list);

    List<Map<String, String>> selectClassSingleSubjectChange(String grade, String subject, String classNumber);

    List<Exam> selectRecentlyExams(String grade);

    ModelAndView setCutOffGrade(HttpServletRequest request,String startYear);

    String disposeStartYear(HttpServletRequest request,String startYear);

    String disposeExam(String exam,List<Exam> examList);

    List<String> getGradeList(HttpServletRequest request);

    Integer calcPassLineCount(String examTable, Integer cutOffGrade);

    ModelAndView selectClassesRanking(HttpServletRequest request, String examTable, String grade, String ranking, String subject);
}
