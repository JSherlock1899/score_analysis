package com.slxy.analysis.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.slxy.analysis.model.ClassGrade;
import com.slxy.analysis.model.Exam;
import com.slxy.analysis.model.Grade;
import com.slxy.analysis.model.Teacher;
import com.slxy.analysis.service.TeacherService;
import com.slxy.analysis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/9/4 20:17
 */
@Controller
@RequestMapping("teacher")
public class TeacherController {

    @Autowired
    UserService userService;
    @Autowired
    TeacherService teacherService;

    /**
     * 根据考试名和班级返回对应的成绩列表
     * @param exam 考试名
     * @param classNumber 班级
     * @param pageNum 当前页 默认为第一页
     * @param pageSize 每页数据条数 默认为10条
     * @return
     */
    @RequestMapping("selectStuGrade")
    public ModelAndView listStudentGrade(HttpServletRequest request, @RequestParam(defaultValue = "1")
            int pageNum, @RequestParam(defaultValue = "5") int pageSize,  @RequestParam(defaultValue = "18_students_grades_20190728") String exam,
            @RequestParam(defaultValue = "1801") String classNumber, @RequestParam(defaultValue = "total_point") String subject,
            @RequestParam(defaultValue = "asc") String sort, Map<String, Object> map){
        //初始化老师所带的班级的下拉框
        List<String> classList = teacherService.listClass(request);
        //初始化考试名称的下拉框
        HashSet<Exam> examList = userService.getExam(teacherService.getTeacherGrade(classList));
        //传入当前页以及每页的数据条数
        PageHelper.startPage(pageNum, pageSize);
        //获取学生成绩信息
        List<Grade> studentGrades = teacherService.getStudentGrades(exam, classNumber, pageNum, pageSize, subject,sort);
        //返回该班级各科的平均成绩
        ClassGrade averageGrade = userService.getClassAverageGrade(exam,classNumber);
        //使用PageInfo包装查询结果，只需要将pageInfo交给页面就可以
        PageInfo<Grade> pageInfo = new PageInfo(studentGrades);
        //pageINfo封装了分页的详细信息，也可以指定连续显示的页数
        map.put("pageInfo", pageInfo);
        //学生成绩信息
        map.put("studentGrades", studentGrades);
        //班级各科平均成绩
        map.put("averageGrade", averageGrade);
        //考试名称列表
        map.put("examList", examList);
        //所选考试
        map.put("exam", exam);
        //所选班级
        map.put("classNumber", classNumber);
        //排序方式
        map.put("sort", sort);
        //所选排序科目
        map.put("subject", subject);
        //班级列表
        map.put("classList", classList);
        ModelAndView mv = new ModelAndView();
        mv.addAllObjects(map);
        mv.setViewName("teacher/studentGradeList");
        return mv;
    }


    /**
     * 根据考试名和年级返回对应的成绩列表
     * @param exam 考试名
     * @param startYear 年级
     * @param pageNum 当前页 默认为第一页
     * @param pageSize 每页数据条数 默认为10条
     * @param map 用来存发传到前端的对象
     * @return
     */
    @RequestMapping("selectClassGrade")
    public ModelAndView listClassGrade(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize,
                                       @RequestParam(defaultValue = "18_students_grades_20190728") String exam,
                                       @RequestParam(defaultValue = "18") String startYear,@RequestParam(defaultValue = "total_point") String subject,
                                       @RequestParam(defaultValue = "asc") String sort, Map<String, Object> map){
        //初始化考试名称的下拉框
        HashSet<Exam> examList = userService.getExam(Arrays.asList(startYear));
        //获取班级成绩表名
        String classGradeTable = exam.replace("students", "class");
        //传入当前页以及每页的数据条数
        PageHelper.startPage(pageNum, pageSize);
        //获取各班级成绩信息
        List<ClassGrade> classGrades = teacherService.getClassesGrades(classGradeTable, startYear, pageNum, pageSize, subject, sort);
        //使用PageInfo包装查询结果，只需要将pageInfo交给页面就可以
        PageInfo<Grade> pageInfo = new PageInfo(classGrades);
        //pageINfo封装了分页的详细信息，也可以指定连续显示的页数
        map.put("pageInfo", pageInfo);
        //各班级的成绩信息
        map.put("classGrades", classGrades);
        //考试名称列表
        map.put("examList", examList);
        //当前所选考试
        map.put("exam", exam);
        //当前所选年级
        map.put("startYear", startYear);
        //排序方式
        map.put("sort", sort);
        //所选排序科目
        map.put("subject", subject);
        ModelAndView mv = new ModelAndView();
        mv.addAllObjects(map);
        mv.setViewName("teacher/classGradeList");
        return mv;
    }

    /**
     * 查看班级详情
     * @return
     */
    @RequestMapping("viewDetail")
    public ModelAndView viewDetail(String classNumber, String exam, Map<String, Object> map){
        List<Grade> classGradeList = teacherService.getClassGradeList(classNumber);
        //只取近五次成绩进行可视化
        if(classGradeList.size() > 5){
            for (int i = 0; i < classGradeList.size() - 5; i++ ){
                classGradeList.remove(i);
            }
        }
        ModelAndView mv = new ModelAndView();
        List<Map<String, String>> studnetRankingDistribute = teacherService.getStudnetRankingDistribute(exam, classNumber);
        System.out.println(classGradeList);
        System.out.println(studnetRankingDistribute);
        JSONArray jsonList = JSONArray.parseArray(JSON.toJSONString(classGradeList));
        JSONArray rankingDistribute = JSONArray.parseArray(JSON.toJSONString(studnetRankingDistribute));
        System.out.println(rankingDistribute);
        mv.addObject("jsonList", jsonList);
        mv.addObject("rankingDistribute", rankingDistribute);
        mv.setViewName("teacher/classDetail");
        return mv;
    }

    /**
     * 获取教师个人信息
     * @param request
     * @return
     */
    @RequestMapping("getPersonalInformation")
    public ModelAndView getPersonalInformation(HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("username");
        Teacher teacher = teacherService.getTeacherById(username);
        ModelAndView mv = new ModelAndView();
        mv.addObject("teacher", teacher);
        mv.setViewName("teacher/teacherPersonalInfo");
        return mv;
    }

}
