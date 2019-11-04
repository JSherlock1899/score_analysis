package com.slxy.analysis.student.tools;

import com.slxy.analysis.teacher.model.Exam;

import java.util.concurrent.CopyOnWriteArrayList;

public class ExamInformationSort {
    /*
    将查出来的考试信息进行时间排序，最新一次在前反之在后
     */
    public static CopyOnWriteArrayList<Exam> examSortredce(CopyOnWriteArrayList<Exam> exam){
        Exam[] exma = exam.toArray(new Exam[exam.size()]);
        CopyOnWriteArrayList<Exam> exs = new CopyOnWriteArrayList<>();
        for(int i=0;i<exma.length;i++){
            for(int j=0;j<exma.length-i-1;j++){
                if(Integer.parseInt(exma[j].getExamTime())<Integer.parseInt(exma[j+1].getExamTime())){
                    Exam ex=exma[j];
                    exma[j]=exma[j+1];
                    exma[j+1]=ex;
                }
            }
        }
        for (Exam e:exma) { exs.add(e); }
        return exs;
    }
}
