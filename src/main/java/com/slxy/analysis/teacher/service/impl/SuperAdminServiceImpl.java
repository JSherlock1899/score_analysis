package com.slxy.analysis.teacher.service.impl;

import com.slxy.analysis.teacher.mapper.SuperAdminMapper;
import com.slxy.analysis.teacher.model.Exam;
import com.slxy.analysis.teacher.model.Student;
import com.slxy.analysis.teacher.model.Teacher;
import com.slxy.analysis.teacher.service.SuperAdminService;
import com.slxy.analysis.teacher.util.LinuxCmdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/9/7 13:30
 */
@Service
public class SuperAdminServiceImpl implements SuperAdminService {

    @Autowired
    SuperAdminMapper superAdminMapper;

    @Override
    public void createExamTable(String grade, String examTime) {
        examTime = examTime.replace("-", "");
        String tableName01 = grade + "_students_grades_" + examTime;
        superAdminMapper.createStudentsGradesTable(tableName01);
        String tableName02 = grade + "_students_ranking_" + examTime;
        superAdminMapper.createStudentsRankingTable(tableName02);
        String tableName03 = grade + "_class_grades_" + examTime;
        superAdminMapper.createClassGrades(tableName03);
    }

    @Override
    public void createExamRecord(String grade, String examTime, String examName) {
        examTime = examTime.replace("-", "");
        //该次考试对应的数据表名
        String examTalbe = grade + "_students_grades_" + examTime;
        superAdminMapper.createExamRecord(examTalbe, grade, examTime, examName);
    }

    /**
     * 创建学生排名表
     * @param examTable 表名
     */
    @Override
    public void createStudentsRankingTable(String examTable) {
        superAdminMapper.createStudentsRankingTable(examTable);
    }

    /**
     * 创建班级成绩表
     * @param examTable 表名
     */
    @Override
    public void createClassGrades(String examTable) {
        superAdminMapper.createClassGrades(examTable);
    }

    /**
     * 创建学生成绩表
     * @param examTable
     */
    @Override
    public void createStudentsGradesTable(String examTable) {
        superAdminMapper.createStudentsGradesTable(examTable);
    }


    @Override
    public void callAverageGrade(String subjects, String avgSubject, String examTable, String classGradeTable, String start_year) {
        superAdminMapper.callAverageGrade(subjects,avgSubject,examTable,classGradeTable,start_year);
    }

    @Override
    public void callClassRanking(String gradeTable, String rankingTable, String subjects, String ranking, String classNumber) {
        superAdminMapper.callClassRanking(gradeTable,rankingTable,subjects,ranking,classNumber);
    }

    @Override
    public void callSchoolRanking(String gradeTable, String rankingTable, String subjects, String ranking, String classNumber) {
        superAdminMapper.callSchoolRanking(gradeTable,rankingTable,subjects,ranking,classNumber);
    }

    @Override
    public void callStudentRanking(String gradeTable, String rankingTable, List<String> classList){
        //循环计算并插入各班学生的排名信息
        for (String c : classList){
            for (int i = 0; i < Student.SUBJECT.length; i++ ){
                //学生成绩表中的科目字段名
                String subjects = Student.SUBJECT[i] + "_grades";
                //拼接字符串得到班级排名字段
                String classRanking = Student.SUBJECT[i] + "_class_ranking";
                //拼接字符串得到校排名字段
                String schoolRanking = Student.SUBJECT[i] + "_school_ranking";
                //调用存储过程计算并插入学生的班排名
                callClassRanking(gradeTable, rankingTable, subjects, classRanking,c);
                //调用存储过程计算并插入学生的校排名
                callSchoolRanking(gradeTable, rankingTable, subjects, schoolRanking,c);
            }
        }
    }

    /**
     * 初始化学生排名表
     * @param gradeTable 学生成绩表
     * @param rankingTable 学生排名表
     */
    @Override
    public void callInitRankingTable(String gradeTable, String rankingTable) {
        superAdminMapper.callInitRankingTable(gradeTable,rankingTable);
    }

    @Override
    public void doBackups(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        String cmd1 = "docker exec -it mysql01 bash";
        String cmd2 = "mysqldump -uroot -proot score_analysis > /home/backup/" + df.format(new Date());
        LinuxCmdUtils.executeLinuxCmd(cmd1);
        LinuxCmdUtils.executeLinuxCmd(cmd2);
    }

    /**
     * 获取所有考试
     * @return
     */
    @Override
    public List<Exam> selectAllExam() {
        return superAdminMapper.selectAllExam();
    }

    @Override
    public List<Teacher> selectTeacherListByRole(String role) {
        return superAdminMapper.selectTeacherListByRole(role);
    }

    @Override
    public ModelAndView selectTeacherListByName(String name) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("superAdmin/updateTeacherInfo");
        //若输入了具体的名字则对其进行模糊查询，若没有则返回普通教师列表
        if (!"".equals(name)){
            mv.addObject("teacherList", superAdminMapper.selectTeacherListByName(name));
            return mv;
        }else {
            mv.addObject("teacherList", superAdminMapper.selectTeacherListByRole("2"));
            return mv;
        }

    }

    @Override
    public ModelAndView updateTeacherAuthority(String id, String role) {
        ModelAndView mv = new ModelAndView();
        int i = superAdminMapper.updateTeacherAuthority(role, id);
        System.out.println("id = " + id);
        System.out.println("i = " + i);
        if (i == 0){
            mv.addObject("msg", "表已存在");
        }
        mv.setViewName("/superAdmin/authorityManagement");
        return mv;
    }

}
