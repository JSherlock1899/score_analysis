package com.slxy.analysis.teacher.controller;

import com.slxy.analysis.student.service.StService;
import com.slxy.analysis.teacher.model.Exam;
import com.slxy.analysis.teacher.model.Student;
import com.slxy.analysis.teacher.model.Teacher;
import com.slxy.analysis.teacher.service.MailService;
import com.slxy.analysis.teacher.service.SuperAdminService;
import com.slxy.analysis.teacher.service.TeacherService;
import com.slxy.analysis.teacher.utils.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.slxy.analysis.teacher.utils.MySQLUtils.dbBackup;

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
    @Autowired
    StService stServiceimp;
    @Autowired
    MailService mailService;

    /**
     * 创建各考试用表
     * @return
     */
    @Operation(name="创建各考试用表")
    @RequestMapping("createTable")
    public ModelAndView createTable(String grade, String examTime, String examName){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("ttt");
        try{
            //创建三张表
//            superAdminService.createExamTable(grade, examTime);
            //创建考试记录
            superAdminService.createExamRecord(grade ,examTime, examName);
        }catch (Exception e){
            e.printStackTrace();
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
    @Operation(name="调用存储过程生成各班平均分")
    @RequestMapping("callAverageGrade")
    public String callAverageGrade(String examTable, String classGradeTable, String start_year){
        for (int i = 0; i < Student.SUBJECT.length; i++ ){
            //学生成绩表中的科目字段名
            String subject = Student.SUBJECT[i] + "_grades";
            //班级成绩表的各科目平均成绩字段名
            String avgSubject = Student.SUBJECT[i] + "_average_grades";
            superAdminService.callAverageGrade(subject,avgSubject,examTable,classGradeTable,start_year);
        }
        return "ttt";
    }

    @Operation(name="调用存储过程生成学生排名")
    @RequestMapping("callStudentRanking")
    public void callStudentRanking(String startYear,String gradeTable, String rankingTable){
        List<String> classList = teacherService.getGradeClass(startYear);
        superAdminService.callStudentRanking(gradeTable, rankingTable, classList);
    }

    /**
     * 跳转到管理后台
     * @return
     */
    @Operation(name="访问管理后台")
    @RequestMapping("goSuperAdminIndex")
    public String goSuperAdminIndex(){
        return "superAdmin/superAdminIndex";
    }

    /**
     * 跳转到创建考试页面
     * @return
     */
    @Operation(name="访问创建考试页面")
    @RequestMapping("goCreateExam")
    public ModelAndView goCreateExam(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        //首次查询时初始化年级
        List<String> presentGrade = teacherService.getPresentGrade();
        //查询所有考试信息
        List<Exam> exams = superAdminService.selectAllExam();
        mv.addObject("exams", exams);
        mv.addObject("presentGrade", presentGrade);
        mv.setViewName("superAdmin/createExam");
        return mv;
    }

    /**
     * 跳转到备份与恢复页面
     * @return
     */
    @Operation(name="访问备份与恢复页面")
    @RequestMapping("goBackups")
    public ModelAndView goBackups(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("superAdmin/backups");
        return mv;
    }

    /**
     * 跳转到权限管理页面
     * @return
     */
    @Operation(name="访问权限管理页面")
    @RequestMapping("authorityManagement")
    public ModelAndView authorityManagement(@RequestParam(defaultValue = "2") String role){
        ModelAndView mv = new ModelAndView();
        List<Teacher> teacherList = superAdminService.selectTeacherListByRole(role);
        mv.addObject("teacherList", teacherList);
        mv.addObject("role", role);
        mv.setViewName("superAdmin/authorityManagement");
        return mv;
    }

    /**
     * 跳转到修改教师信息页面
     * @return
     */
    @Operation(name="访问修改教师信息页面")
    @RequestMapping("updateTeacherInfo")
    public ModelAndView updateTeacherInfo(@RequestParam(required = false) String name,int pageNum, @RequestParam(defaultValue = "5") int pageSize){
        ModelAndView mv  = superAdminService.selectTeacherListByName(name,pageNum,pageSize);
        return mv;
    }

    @RequestMapping("doBackup")
    public String doBackups(){
        try {
            dbBackup("root","root","39.107.90.231","score_analysis");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "superAdmin/backups";
    }

    /**
     * 查看教师详细信息
     * @param id 工号
     * @return
     */
    @Operation(name="查看自己的详细信息")
    @RequestMapping("showTeacherInfo")
    public ModelAndView showTeacherInfo(String id){
        ModelAndView mv = new ModelAndView();
        Teacher teacher = teacherService.getTeacherById(id);
        mv.addObject("teacher", teacher);
        mv.setViewName("teacher/teacherPersonalInfo");
        return mv;
    }

    @Operation(name="查看教师详细信息")
    @RequestMapping("updateTeacherAuthority")
    public ModelAndView updateTeacherAuthority(String id, String role){
        ModelAndView mv = superAdminService.updateTeacherAuthority(id, role);
        return mv;
    }

    /*
    跳到导入excel页面
     */
    @Operation(name="访问导入excel页面")
    @RequestMapping("ie")
    public ModelAndView InExcelPage(ModelAndView mv){
        CopyOnWriteArrayList<Exam> all = stServiceimp.getExaminfomation("all");
        mv.addObject("ex",all);
        mv.setViewName("superAdmin/excel");
        return mv;
    }

    /*
    导excel
     */
    @Operation(name="导入excel")
    @RequestMapping("/eh")
    public ModelAndView ExcelHandle(HttpServletRequest req, ModelAndView mv){
        superAdminService.uploadfile(req);
        mv.setViewName("superAdmin/excel");
        return mv;
    }

    /**
     * 更新教师信息
     * @param teacher 教师实体
     * @return
     */
    @Operation(name="更新教师信息")
    @RequestMapping("updateTerInfo")
    public ModelAndView updateTerInfo(Teacher teacher){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("superAdmin/updateTeacherInfo");
        String msg;
        int i = superAdminService.updateTerInfo(teacher);
        if (i == 0){
            msg = "更新失败！";
        }else{
            msg = "更新成功！";
        }
        mv.addObject("msg", msg);
        return mv;
    }

    /**
     * 根据教师id删除教师
     * @param id
     * @return
     */
    @Operation(name="根据教师id删除教师")
    @RequestMapping("delTer")
    public ModelAndView delTer(String id){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("superAdmin/updateTeacherInfo");
        superAdminService.delTer(id);
        return mv;
    }


}
