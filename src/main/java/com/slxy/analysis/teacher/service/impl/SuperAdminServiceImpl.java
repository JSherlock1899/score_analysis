package com.slxy.analysis.teacher.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.slxy.analysis.student.tools.ExcelHandelTools;
import com.slxy.analysis.student.tools.UploadFileTools;
import com.slxy.analysis.teacher.mapper.SuperAdminMapper;
import com.slxy.analysis.teacher.mapper.TeacherMapper;
import com.slxy.analysis.teacher.mapper.UserMapper;
import com.slxy.analysis.teacher.model.Exam;
import com.slxy.analysis.teacher.model.Grade;
import com.slxy.analysis.teacher.model.Student;
import com.slxy.analysis.teacher.model.Teacher;
import com.slxy.analysis.teacher.service.SuperAdminService;
import com.slxy.analysis.teacher.service.UserService;
import com.slxy.analysis.teacher.utils.LinuxCmdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
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
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    SuperAdminService superAdminService;
    @Autowired
    UserService userService;

    /**
     * 创建三张考试用表
     * @param grade
     * @param examTime
     */
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
        superAdminService.insertExam(examTalbe, grade, examTime, examName);
    }


    @Override
    public List<Exam> insertExam(String examTalbe, String grade, String examTime, String examName) {
        superAdminMapper.createExamRecord(examTalbe, grade, examTime, examName);
        List<Exam> examByRedis = userMapper.getExam(grade);
        for (Exam e : examByRedis) {
            System.out.println("e = " + e.getExamName());
        }
        return examByRedis;
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
    public ModelAndView selectTeacherListByName(String name, int pageNum, int pageSize) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("superAdmin/updateTeacherInfo");
        //若输入了具体的名字则对其进行模糊查询，若没有则返回普通教师列表
        PageHelper.startPage(pageNum, pageSize);
        List<Teacher> teachers = superAdminMapper.selectTeacherListByName(name);
        //使用PageInfo包装查询结果，只需要将pageInfo交给页面就可以
        PageInfo<Teacher> pageInfo = new PageInfo(teachers);
        //pageINfo封装了分页的详细信息，也可以指定连续显示的页数
        mv.addObject("pageInfo", pageInfo);
        if (!"".equals(name)){
            mv.addObject("teacherList", teachers);
            return mv;
        }else {
            mv.addObject("teacherList", superAdminMapper.selectTeacherListByRole("2"));
            return mv;
        }

    }

    /**
     * 修改教师权限(缓存)
     * @param id
     * @param role
     * @return
     */
    public String updateAuthority(String id, String role){
       superAdminMapper.updateTeacherAuthority(id, role);
        return teacherMapper.getTeacherRole(id);
    }

    @Override
    public ModelAndView updateTeacherAuthority(String id, String role) {
        ModelAndView mv = new ModelAndView();
        String i = superAdminService.updateAuthority(id, role);
        if (i.equals(role)){
            mv.addObject("msg", "更改失败");
        }
        mv.setViewName("/superAdmin/authorityManagement");
        return mv;
    }

    /*
   将当前excel表上传至服务器
    */
    public boolean uploadfile(HttpServletRequest req){
        String realPath = req.getSession().getServletContext().getRealPath("");
        try {
            String uploadfile = UploadFileTools.uploadfile(req, realPath);
            ExcelHandelTools.readExcel(new Grade(),uploadfile);
            File file = new File(uploadfile);
            if(file.exists()){
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public int updateTerInfo(Teacher teacher) {
        return superAdminMapper.updateTerInfo(teacher.getId(), teacher.getName(), teacher.getSex(), teacher.getNation(), teacher.getIdCard(), teacher.getTelephone(),teacher.getNativePlace(), teacher.getHomeAddress(), teacher.getJobGrade(), teacher.getPolStat(), teacher.getGraduateSchool(), teacher.getSubject());
    }

    @Override
    public void delTer(String id) {
        superAdminMapper.delTer(id);
    }
}
