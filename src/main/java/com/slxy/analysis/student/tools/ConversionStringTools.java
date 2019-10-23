package com.slxy.analysis.student.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
字符串转换工具类
 */
public class ConversionStringTools {
    /*
    将传入的考试字符串转换成排名字符串
     */
    public static String GreadtoRank(String exem){
        //18_students_grades_20190728
     return exem.replaceAll("grades","ranking");
    }

    /*
       将考试字符串转换为平均成绩表名
       //18_students_grades_20190728  to 18_class_grades_20190728
     */
    public static String GradetoAverage(String exam){
        return exam.replaceAll("students","class");
    }
    /*
    默认查询成绩
     */
    public String defaultGrade(String ClassName){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(new Date()).substring(0,2);
        return year;
    }
}
