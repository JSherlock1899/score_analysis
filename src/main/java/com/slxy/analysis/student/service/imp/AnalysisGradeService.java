package com.slxy.analysis.student.service.imp;

import com.slxy.analysis.student.Mapper.ExamInfomation;
import com.slxy.analysis.student.Mapper.StudentGradeMapper;
import com.slxy.analysis.student.POJO.ClassRank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class AnalysisGradeService {

    @Autowired
    ExamInfomation ef;
    @Autowired
    StudentGradeMapper sgm;

    /*

     */
    public ModelAndView analysis(ModelAndView mv){

        return mv;
    }

    /*
    判断当前场次考试是否及格,放入session中按照以下顺序，0表示未及格，1表示及格
    '语文','数学','英语','物理','化学','生物','历史','政治','地理','信息技术'
    参数qualified为用户定义的及格线
     */
    public String ifQualified(ClassRank grade, int qualified){
        String Qualifiedsituation="";
        if(Integer.parseInt(grade.getChineseGrades())>=qualified){
            Qualifiedsituation=Qualifiedsituation+"1";
        }else{
            Qualifiedsituation=Qualifiedsituation+"0";
        }
        if(Integer.parseInt(grade.getMathGrades())>=qualified){
            Qualifiedsituation=Qualifiedsituation+"1";
        }else{
            Qualifiedsituation=Qualifiedsituation+"0";
        }
        if(Integer.parseInt(grade.getEnglishGrades())>=qualified){
            Qualifiedsituation=Qualifiedsituation+"1";
        }else{
            Qualifiedsituation=Qualifiedsituation+"0";
        }
        if(Integer.parseInt(grade.getPhysicsGrades())>=qualified){
            Qualifiedsituation=Qualifiedsituation+"1";
        }else{
            Qualifiedsituation=Qualifiedsituation+"0";
        }
        if(Integer.parseInt(grade.getChemistryGrades())>=qualified){
            Qualifiedsituation=Qualifiedsituation+"1";
        }else{
            Qualifiedsituation=Qualifiedsituation+"0";
        }
        if(Integer.parseInt(grade.getBiologyGrades())>=qualified){
            Qualifiedsituation=Qualifiedsituation+"1";
        }else{
            Qualifiedsituation=Qualifiedsituation+"0";
        }
        if(Integer.parseInt(grade.getHistoryGrades())>=qualified){
            Qualifiedsituation=Qualifiedsituation+"1";
        }else{
            Qualifiedsituation=Qualifiedsituation+"0";
        }
        if(Integer.parseInt(grade.getPoliticsGrades())>=qualified){
            Qualifiedsituation=Qualifiedsituation+"1";
        }else{
            Qualifiedsituation=Qualifiedsituation+"0";
        }
        if(Integer.parseInt(grade.getGeographyGrades())>=qualified){
            Qualifiedsituation=Qualifiedsituation+"1";
        }else{
            Qualifiedsituation=Qualifiedsituation+"0";
        }
        if(Integer.parseInt(grade.getTechnologyGrades())>=qualified){
            Qualifiedsituation=Qualifiedsituation+"1";
        }else{
            Qualifiedsituation=Qualifiedsituation+"0";
        }

        return Qualifiedsituation;


    }

}
