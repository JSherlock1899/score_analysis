package com.slxy.analysis.teacher.controller;

import com.slxy.analysis.teacher.model.Student;
import com.slxy.analysis.teacher.service.SuperAdminService;
import com.slxy.analysis.teacher.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author: sherlock
 * @description: 超级管理员操作类
 * @date: 2019/9/7 13:17
 */

@Controller
@RequestMapping("superAdmin")
public class SuperAdminController {

    @Autowired
    SuperAdminService superAdminService;
    @Autowired
    TeacherService teacherService;

    /**
     * 创建各考试用表
     * @return
     */
    @RequestMapping("createTable")
    public ModelAndView createTable(String grade, String examTime, String examName){
        System.out.println(examName);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("ttt");
        try{
            //创建三张表
            superAdminService.createExamTable(grade, examTime);
        }catch (Exception e){
            e.printStackTrace();
            mv.addObject("msg", "表已存在");
            return mv;
        }
        mv.addObject("msg", "建表成功");
        return mv;
    }

    /**
     * 调用存储过程生成各班平均分
     * @param examTable 考试表
     * @param classGradeTable 班级成绩表
     * @param start_year 入学年份（即年级）
     * @return
     */
    @RequestMapping("callAverageGrade")
    public String callAverageGrade(String examTable, String classGradeTable, String start_year){
        for (int i = 0; i < Student.SUBJECT.length; i++ ){
            //学生成绩表中的科目字段名
            String subject = Student.SUBJECT[i] + "_grades";
            //班级成绩表的各科目平均成绩字段名
            String avgSubject = Student.SUBJECT[i] + "_average_grades";
            superAdminService.callAverageGrade(subject,avgSubject,examTable,classGradeTable,start_year);
        }
        return "ttt";
    }

    @RequestMapping("callStudentRanking")
    public void callStudentRanking(String startYear,String gradeTable, String rankingTable){
        List<String> classList = teacherService.getGradeClass(startYear);
        superAdminService.callStudentRanking(gradeTable, rankingTable, classList);
    }

    /**
     * 跳转到管理后台
     * @return
     */
    @RequestMapping("goSuperAdminIndex")
    public String goSuperAdminIndex(){
        return "superAdmin/superAdminIndex";
    }

    /**
     * 跳转到创建考试页面
     * @return
     */
    @RequestMapping("goCreateExam")
    public ModelAndView goCreateExam(){
        ModelAndView mv = new ModelAndView();
        //首次查询时初始化年级
        List<String> presentGrade = teacherService.getPresentGrade();
        mv.setViewName("superAdmin/createExam");
        mv.addObject("presentGrade", presentGrade);
        return mv;
    }

    /**
     * 跳转到备份与恢复页面
     * @return
     */
    @RequestMapping("goBackups")
    public ModelAndView goBackups(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("superAdmin/backups");
        return mv;
    }

    @RequestMapping("doBackup")
    public void doBackups(){
        System.out.println("!!");
        superAdminService.doBackups();
    }



}
