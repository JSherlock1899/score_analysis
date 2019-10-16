package com.slxy.analysis.service.impl;

import com.slxy.analysis.mapper.TeacherMapper;
import com.slxy.analysis.model.ClassGrade;
import com.slxy.analysis.model.Grade;
import com.slxy.analysis.model.Teacher;
import com.slxy.analysis.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/9/1 11:06
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    TeacherMapper teacherMapper;

    @Override
    @Cacheable(cacheNames = "teacher")
    public Teacher getTeacherById(String id) {
        return teacherMapper.getTeacherById(id);
    }

    @Override
    @Cacheable(cacheNames = "teacherRole")
    public String getTeacherRole(String id) {
        return teacherMapper.getTeacherRole(id);
    }

    @Override
    @Cacheable(cacheNames = "teacherNameAndSubject")
    public Teacher getTeacherNameAndSubject(String id) {
        return teacherMapper.getTeacherNameAndSubject(id);
    }

    @Override
    @Cacheable(cacheNames = "teacherClass")
    public List<String> getTeacherClass(String subject, String id){
        return teacherMapper.getTeacherClass(subject, id);
    }

    /**
     * 获取学生成绩列表
     * @param exam
     * @param classNumber
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<Grade> getStudentGrades(String exam, String classNumber,Integer pageNum, Integer pageSize,String subject,String sort) {
        //获取班级所处年级
        if (classNumber != null) {
            String grade = classNumber.substring(0, 2);
            subject = subject + "_grades";
            return teacherMapper.getStudentGrades(exam, classNumber, grade,pageNum,pageSize,subject,sort);
        }
        return null;
    }


    /**
     * 获取该教师所教的班级列表
     * @param request
     * @return
     */
    @Override
    public List<String> listClass(HttpServletRequest request){
        //从session中获取username
        String username = (String) request.getSession().getAttribute("username");
        //获取教师所教的课程
        Teacher teacher = getTeacherNameAndSubject(username);
        //根据教师所教的课程和工号获取教师所教的班级
        List<String> classList = getTeacherClass(teacher.getSubject(), username);
        return classList;
    }

    /**
     *获取各班级的成绩
     */
    @Override
    @Cacheable("ClassGrades")
    public List<ClassGrade> getClassesGrades(String classGradeTable, String startYear, Integer pageNum, Integer pageSize,String subject, String sort) {
        subject = subject + "_average_grades";
        return teacherMapper.getClassesGrades(classGradeTable, startYear, pageNum, pageSize, subject, sort);
    }

    /**
     * 获取某个年级有哪几个班
     * @param startYear 入学年份，也可以看做是年级
     * @return
     */
    @Override
    public List<String> getGradeClass(String startYear) {
        return teacherMapper.getGradeClass(startYear);
    }

    /**
     * 动态查询教师所教的年级（一个教师可能教多个年级）
     * @param classes 教师所教的班级列表
     * @return
     */
    @Override
    public List<String> getTeacherGrade(List<String> classes) {
        String s;
        List<String> grade = new ArrayList<String>();
        for (String c : classes){
            //分割班级字符串，得到前两位数，也就是对应的年级
            s = c.substring(0, 2);
            if (!grade.contains(s)){
                grade.add(s);
            }
        }
        return grade;
    }

    /**
     * 获取每个年级的考试列表
     * @param grade
     * @return
     */
    @Override
    public List<String> getExamList(String grade) {
        return teacherMapper.getExamList(grade);
    }

    /**
     * 获取某班某一次成绩的列表
     * @param examTable
     * @param classNumber
     * @return
     */
    @Override
    public Grade getClassGrades(String examTable, String classNumber) {
        //获取班级成绩的表名
        examTable = examTable.replace("students", "class");
        return teacherMapper.getClassGrades(examTable,classNumber);
    }

    /**
     * 获取各次某班成绩的列表
     * @param classNumber 班级
     * @return
     */
    @Override
    public List<Grade> getClassGradeList(String classNumber){
        String grade = classNumber.substring(0, 2);
        //获取该班级已经进行的考试
        List<String> examList = getExamList(grade);
        List<Grade> grades = new ArrayList<Grade>();
        //逐个查询出该班级各次考试的成绩对象并添加到列表中
        for (String exam : examList){
            //获取单次考试的成绩对象
            Grade classGrades = getClassGrades(exam, classNumber);
            if (classGrades != null){
                //设置考试名
                classGrades.setName(getExamName(exam));
            }
            grades.add(classGrades);
        }
        return grades;
    }

    /**
     * 根据考试表获取考试名
     * @param examTable 考试表
     * @return
     */
    @Override
    public String getExamName(String examTable) {
        return teacherMapper.getExamName(examTable);
    }

    /**
     * 查询全校前num名的学生人数
     * @param examTable 考试表
     * @param classNumber 班级
     * @return
     */
    @Override
    public Integer getTopStudnetNumber(String examTable, String classNumber, Integer num) {
        String rankingTable = examTable.replace("grades", "ranking");
        Integer studnetNumber = teacherMapper.getTopStudnetNumber(rankingTable, classNumber,num);
        return studnetNumber;
    }

    /**
     * 查询某班学生人数
     * @param classNumber
     * @return
     */
    @Override
    public Integer getClassStudentNumber(String classNumber) {
        return teacherMapper.getClassStudentNumber(classNumber);
    }

    /**
     * 获取某班学生在某次考试中的排名分布
     * @param examTable 考试表
     * @param classNumber 班级
     * @return
     */
    public List<Map<String, String>> getStudnetRankingDistribute(String examTable, String classNumber){
        //获取前100名学生人数
        Integer topHundredStudnetNumber = getTopStudnetNumber(examTable, classNumber, 100);
        //获取前200名学生人数
        Integer topTwoHundredStudnetNumber = getTopStudnetNumber(examTable, classNumber, 200);
        //获取前500名学生人数
        Integer topFiveHundredStudnetNumber = getTopStudnetNumber(examTable, classNumber, 500);
        //获取该班级的学生人数
        Integer classStudentNumber = teacherMapper.getClassStudentNumber(classNumber);
        //获取剩余学生人数
        Integer restStudnet = classStudentNumber  - topFiveHundredStudnetNumber;
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("type", "前100名");
        map1.put("number", topHundredStudnetNumber.toString());
        list.add(map1);
        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("type", "100～200名");
        map2.put("number", String.valueOf(topTwoHundredStudnetNumber - topHundredStudnetNumber));
        list.add(map2);
        Map<String, String> map3 = new HashMap<String, String>();
        map3.put("type", "200～500名");
        map3.put("number", String.valueOf(topFiveHundredStudnetNumber - topTwoHundredStudnetNumber));
        list.add(map3);
        Map<String, String> map4 = new HashMap<String, String>();
        map4.put("type", "500名以后");
        map4.put("number", restStudnet.toString());
        list.add(map4);
        return list;
    }
}
