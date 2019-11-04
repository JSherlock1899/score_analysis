package com.slxy.analysis.student.service.imp;


import com.slxy.analysis.student.mapper.ExamInfomation;
import com.slxy.analysis.student.mapper.StudentGradeMapper;
import com.slxy.analysis.student.POJO.ClassRank;
import com.slxy.analysis.student.service.StService;
import com.slxy.analysis.student.tools.ConversionStringTools;
import com.slxy.analysis.student.tools.ExamInformationSort;
import com.slxy.analysis.teacher.model.Exam;
import com.slxy.analysis.teacher.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class StServiceimp implements StService
{
    @Autowired
    StudentGradeMapper sgm;
    @Autowired
    ExamInfomation ef;
    @Autowired
    AnalysisGradeService anas;
/*
查询学生基本成绩
 */
    @Override
    public ModelAndView SearchOne(String exam, HttpServletRequest request, ModelAndView mv) {
        Student student = (Student) request.getSession().getAttribute("student");
        CountDownLatch cdl=new CountDownLatch(1);
        new Thread(()->{
            CopyOnWriteArrayList<Exam> examinfomation
                    = getExaminfomation((String)request.getSession().getAttribute("username"));
            mv.addObject("ex",examinfomation);
            cdl.countDown();
               }).start();
        if (student.getClassNumber() != null) {
            String grade = student.getClassNumber().substring(0, 2);
            ClassRank username = sgm.SearchOneGrade((String) request.getSession().getAttribute("username"),
                    exam, ConversionStringTools.GreadtoRank(exam), student.getClassNumber(),
                    grade, ConversionStringTools.GradetoAverage(exam));
            System.out.println(username);
            anas.ifQualified(username,60);
            mv.addObject("st",username);

        }
        CopyOnWriteArrayList<ClassRank> username = getHistroyTotalGrade((String) request.getSession().getAttribute("username"), request);
        for (ClassRank c:username
             ) {
            System.out.println(c);
        }
        try {
        cdl.await();
        return mv;
       } catch (Exception e) {
           e.printStackTrace();
       }
        return mv;
    }
/*
获取学生基本信息
 */
    @Override
    public Student SeacheStudentBasic(HttpServletRequest request) {
        Student student = (Student) request.getSession().getAttribute("student");
        if (student.getClassNumber() != null) {
            String grade = student.getClassNumber().substring(0, 2);
            return sgm.SeachrByid((String) request.getSession().getAttribute("username"),grade);
        }
        return null;
    }

    /*
    获取考试信息填充下拉菜单
    若id=all则查询所有考试
     */
    @Override
    public CopyOnWriteArrayList<Exam> getExaminfomation(String id) {
        CopyOnWriteArrayList<Exam> examinfomation=null;
        if(!id.equals("all")){
            String year = id.substring(0,2);
            examinfomation = ef.getExaminfomation(year);

        }else{
            examinfomation = ef.getAllExamList();
        }
        return  examinfomation;
    }
/*
获取单科最高和最最低成绩
 */
    public ModelAndView getSingleMAXandMINgrade(ModelAndView mv, String exam){
        ExecutorService executorService= Executors.newFixedThreadPool(2);
        CountDownLatch countDownLatch=new CountDownLatch(2);
        try {
          executorService.execute(()->{
              mv.addObject("max",sgm.getSingleMax(exam));
              countDownLatch.countDown();
          });
          executorService.execute(()->{
              mv.addObject("min",sgm.getSingleMin(exam));
              countDownLatch.countDown();
          });
          countDownLatch.await();
          mv.setViewName("student/analyze");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
          executorService.shutdown();
        }
         return mv;
    }

    /*
    获取历史考试所有成绩
     */
    public CopyOnWriteArrayList<ClassRank> getHistroyTotalGrade(String id,HttpServletRequest req){
        Student student = (Student) req.getSession().getAttribute("student");
       CopyOnWriteArrayList<ClassRank> cr=new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<Exam> examinfomation = getExaminfomation(id);
        examinfomation= ExamInformationSort.examSortredce(examinfomation);
        for (Exam e:examinfomation
             ) {
            String grade = student.getClassNumber().substring(0, 2);
            ClassRank username = sgm.SearchOneGrade((String) req.getSession().getAttribute("username"),
                    e.getTableName(), ConversionStringTools.GreadtoRank(e.getTableName()), student.getClassNumber(),
                    grade, ConversionStringTools.GradetoAverage(e.getTableName()));
            cr.add(username);
        }
        return cr;
    }
}
