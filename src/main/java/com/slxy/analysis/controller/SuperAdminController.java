package com.slxy.analysis.controller;

import com.slxy.analysis.model.Student;
import com.slxy.analysis.service.SuperAdminService;
import com.slxy.analysis.service.TeacherService;
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
    public ModelAndView createTable(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        try{
            superAdminService.createStudentTable("18_students_grades_20190418");
        }catch (Exception e){
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
        return "index";
    }

    @RequestMapping("callStudentRanking")
    public void callStudentRanking(String startYear,String gradeTable, String rankingTable){
        List<String> classList = teacherService.getGradeClass(startYear);
        superAdminService.callStudentRanking(gradeTable, rankingTable, classList);
    }

}
