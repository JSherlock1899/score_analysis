package com.slxy.analysis.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.slxy.analysis.mapper.UserMapper;
import com.slxy.analysis.model.Exam;
import com.slxy.analysis.model.Grade;
import com.slxy.analysis.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/9/4 20:17
 */
@Controller
@RequestMapping("teacher")
public class TeacherController {

    @Autowired
    UserMapper userMapper;
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
    @RequestMapping("grade")
    public ModelAndView listStudentGrade(HttpServletRequest request, @RequestParam(defaultValue = "1")
            int pageNum, @RequestParam(defaultValue = "5") int pageSize,  @RequestParam(defaultValue = "17_students_grades_20190728") String exam,
            @RequestParam(defaultValue = "1701") String classNumber){
        System.out.println("exam = " + exam);
        System.out.println("classNumber = " + classNumber);
        //初始化老师所带的班级的下拉框
        ModelAndView mv = teacherService.listClass(request);
        //初始化考试名称的下拉框
        List<Exam> examList = userMapper.getExam();
//        for (String e : examList){
//            System.out.printf("e = " + e);
//        }
//        exam = "17_students_grades_20190728";
//        classNumber = "1701";
        //传入当前页以及每页的数据条数
        PageHelper.startPage(pageNum, pageSize);
        //获取学生成绩信息
        List<Grade> studentGrades = teacherService.getStudentGrades(exam, classNumber);
        //使用PageInfo包装查询结果，只需要将pageInfo交给页面就可以
        PageInfo<Grade> pageInfo = new PageInfo(studentGrades);
        //pageINfo封装了分页的详细信息，也可以指定连续显示的页数
        mv.addObject("pageInfo", pageInfo);
        //将学生成绩信息传到前端
        mv.addObject("studentGrades", studentGrades);
        //将考试名称列表传到前端
        mv.addObject("examList", examList);
        //将当前所选考试传到前端
        mv.addObject("exam", exam);
        //将当前所选班级传到前端
        mv.addObject("classNumber", classNumber);
        mv.setViewName("gradeList");
        return mv;
    }


}
