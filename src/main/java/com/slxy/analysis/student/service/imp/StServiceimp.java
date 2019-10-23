package com.slxy.analysis.student.service.imp;


import com.slxy.analysis.student.Mapper.ExamInfomation;
import com.slxy.analysis.student.Mapper.StudentGradeMapper;
import com.slxy.analysis.student.POJO.ClassRank;
import com.slxy.analysis.student.service.StService;
import com.slxy.analysis.student.tools.ConversionStringTools;
import com.slxy.analysis.teacher.model.Exam;
import com.slxy.analysis.teacher.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

@Service
public class StServiceimp implements StService
{
    @Autowired
    StudentGradeMapper sgm;
    @Autowired
    ExamInfomation ef;

    @Override
    public void SearchOne(String exam, HttpServletRequest request, ModelAndView mv) {
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
            mv.addObject("st",username);
        }
       try {
        cdl.await();
       } catch (Exception e) {
           e.printStackTrace();
       }

    }

    @Override
    public Student SeacheStudentBasic(HttpServletRequest request) {
        Student student = (Student) request.getSession().getAttribute("student");
        if (student.getClassNumber() != null) {
            String grade = student.getClassNumber().substring(0, 2);
            return sgm.SeachrByid((String) request.getSession().getAttribute("username"),grade);
        }
        return null;
    }

    @Override
    public CopyOnWriteArrayList<Exam> getExaminfomation(String id) {
        String year = id.substring(0,2);
        CopyOnWriteArrayList<Exam> examinfomation = ef.getExaminfomation(year);
        return  examinfomation;
    }
}
