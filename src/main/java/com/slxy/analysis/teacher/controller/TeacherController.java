package com.slxy.analysis.teacher.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.slxy.analysis.teacher.model.ClassGrade;
import com.slxy.analysis.teacher.model.Exam;
import com.slxy.analysis.teacher.model.Grade;
import com.slxy.analysis.teacher.model.Teacher;
import com.slxy.analysis.teacher.service.MailService;
import com.slxy.analysis.teacher.service.RedisService;
import com.slxy.analysis.teacher.service.TeacherService;
import com.slxy.analysis.teacher.service.UserService;
import com.slxy.analysis.teacher.utils.CommonUtils;
import com.slxy.analysis.teacher.utils.MD5Utils;
import com.slxy.analysis.teacher.utils.Operation;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
    @Autowired
    MailService mailService;
    @Autowired
    RedisService redisService;

    Log log = LogFactory.getLog(getClass());

    /**
     * 根据考试名和班级返回对应的成绩列表
     * @param exam 考试名
     * @param classNumber 班级
     * @param pageNum 当前页 默认为第一页
     * @param pageSize 每页数据条数 默认为10条
     * @return
     */
    @Operation(name="查看学生成绩")
    @RequestMapping("selectStuGrade")
    public ModelAndView listStudentGrade(HttpServletRequest request, @RequestParam(defaultValue = "1")
            int pageNum, @RequestParam(defaultValue = "5") int pageSize,  @RequestParam(required = false) String exam,
            @RequestParam(required = false) String classNumber, @RequestParam(defaultValue = "total_point") String subject,
            @RequestParam(defaultValue = "desc") String sort, @RequestParam(required = false) String startYear,Map<String, Object> map){
        subject = subject.replace(" reactive", "");
        //获取当前教师权限角色
        String role = teacherService.getTeacherRole((String) request.getSession().getAttribute("username"));
        System.out.println("role = " + role);
        //查询教师备注(来判断该教师属于哪个年级)
        String remark = teacherService.getTeacherRemark((String) request.getSession().getAttribute("username"));
        //首次查询时初始化年级
        List<String> presentGrade = teacherService.getGradeList(request, teacherService.getPresentGrade());
        List<String> classList = teacherService.getClassList(request, role, remark, presentGrade.get(0), startYear);
        //初始化考试名称的下拉框
        HashSet<Exam> examList = userService.getExam(teacherService.getTeacherGrade(classList));
        //传入当前页以及每页的数据条数
        PageHelper.startPage(pageNum, pageSize);
        //返回该班级各科的平均成绩
        ClassGrade averageGrade = null;
        List<Grade> studentGrades = null;
        //首次加载使取一个合适的exam的值
        if (exam == null || classNumber == null){
            Exam arrayExam = examList.iterator().next();
            if ("2".equals(role) || "3".equals(role)){
                //初始化班级
                classNumber = classList.get(0);
            }else if("4".equals(role)){
                classNumber = remark.substring(0, 2) + "01";
            }else if("5".equals(role) || "6" .equals(role)){
                if (startYear == null){
                    startYear = presentGrade.get(0);
                }
                classNumber = startYear + "01";
            }
            //当换年级时exam传入值为null
            if (exam == null){
                exam = arrayExam.getTableName();
            }
            averageGrade = userService.getClassAverageGrade(exam,classNumber);
            //获取学生成绩信息
            studentGrades = teacherService.getStudentGrades(exam, classNumber, pageNum, pageSize, subject,sort);
        }else{
            averageGrade = userService.getClassAverageGrade(exam,classNumber);
            //获取学生成绩信息
            studentGrades = teacherService.getStudentGrades(exam, classNumber, pageNum, pageSize, subject,sort);
        }
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
        //教导主任及更高权限查询时所选的年级
        map.put("startYear", startYear);
        //当前所有的年级
        map.put("presentGrade", presentGrade);
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
    @Operation(name="查看班级成绩")
    @RequestMapping("selectClassGrade")
    public ModelAndView listClassGrade(HttpServletRequest request,@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize,
                                       @RequestParam(required = false) String exam, @RequestParam(required = false) String startYear,@RequestParam(defaultValue = "total_point") String subject,
                                       @RequestParam(defaultValue = "asc") String sort, Map<String, Object> map){
        //首次查询时初始化年级
        List<String> presentGrade = teacherService.getGradeList(request, teacherService.getPresentGrade());
        if (startYear == null){
            startYear = presentGrade.get(0);
        }
        //初始化考试名称的下拉框
        HashSet<Exam> examList = userService.getExam(Arrays.asList(startYear));
        Exam arrayExam = examList.iterator().next();
        if (exam == null){
            exam = arrayExam.getTableName();
        }
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
        //年级信息
        map.put("presentGrade", presentGrade);
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
    @Operation(name="查看班级成绩详情")
    @RequestMapping("viewDetail")
    public ModelAndView viewDetail(String classNumber, String exam, Map<String, Object> map){
        List<Grade> classGradeList = teacherService.getClassGradeList(classNumber);
        //只取近五次成绩进行可视化
        classGradeList = teacherService.disposeList(classGradeList);
        ModelAndView mv = new ModelAndView();
        List<Map<String, String>> studnetRankingDistribute = teacherService.getStudnetRankingDistribute(exam, classNumber);
        JSONArray jsonList = JSONArray.parseArray(JSON.toJSONString(classGradeList));
        JSONArray rankingDistribute = JSONArray.parseArray(JSON.toJSONString(studnetRankingDistribute));
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
    @Operation(name="查看个人信息")
    @RequestMapping("getPersonalInformation")
    public ModelAndView getPersonalInformation(HttpServletRequest request, String id){
        String username = (String) request.getSession().getAttribute("username");
        Teacher teacher = teacherService.getTeacherById(username);
        ModelAndView mv = new ModelAndView();
        mv.addObject("teacher", teacher);
        mv.setViewName("teacher/teacherPersonalInfo");
        return mv;
    }

    /**
     * 设置分数线来宏观的看本次考试的情况
     * @param exam
     * @param startYear
     * @param cutOffGrade 分数线
     * @return
     */
    @Operation(name="查看某次考试的情况")
    @RequestMapping("setCutOffGrade")
    public ModelAndView setCutOffGrade(HttpServletRequest request,@RequestParam(required = false) String exam, @RequestParam(required = false) String startYear
            ,@RequestParam(defaultValue = "500") String cutOffGrade){
        //初始化查询的年级
        startYear = teacherService.disposeStartYear(request, startYear);
        ModelAndView mv = teacherService.setCutOffGrade(request, startYear);
        //获取显示的考试列表
        List<Exam> examList = teacherService.getExamList(startYear);
        //初始化考试表值
        exam = teacherService.disposeExam(exam, examList);
        //获取过线人数总和
        Integer passLineCount = teacherService.calcPassLineCount(exam, Integer.valueOf(cutOffGrade));
        List<Map<String, String>> map = teacherService.selectPassLineStudents(startYear, exam, cutOffGrade);
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(map));
        //各班过线人数集合
        mv.addObject("map", map);
        //json集合
        mv.addObject("jsonArray", jsonArray);
        //考试名
        mv.addObject("exam", exam);
        //入学年份
        mv.addObject("startYear", startYear);
        //分数线
        mv.addObject("cutOffGrade", cutOffGrade);
        //考试列表
        mv.addObject("examList", examList);
        //过线人数总和
        mv.addObject("passLineCount", passLineCount);
        mv.setViewName("teacher/totalAnalysis");
        return mv;
    }

    @RequestMapping("selectClassesRanking")
    @Operation(name="查看班级各科排名分析")
    public ModelAndView selectClassesRanking(HttpServletRequest request, @RequestParam(required = false,value = "exam") String examTable,@RequestParam(value = "startYear", required = false) String grade, @RequestParam(value = "ranking", defaultValue = "100") String ranking,@RequestParam(value = "subject", defaultValue = "total_point") String subject){
        ModelAndView mv = teacherService.selectClassesRanking(request, examTable, grade, ranking, subject);
        mv.setViewName("teacher/subjectAnalysis");
        mv.addObject("exam", examTable);
        mv.addObject("subject", subject);
        mv.addObject("ranking", ranking);
        return mv;
    }

    /**
     * 查看班级历次考试分析
     * @param startYear
     * @param subject
     * @param classNumber
     * @return
     */
    @RequestMapping("showGradesVariation")
    @Operation(name="查看班级历次考试分析")
    public ModelAndView showGradesVariation(@RequestParam(required = false) String startYear,@RequestParam(defaultValue = "total_point") String subject,
                                        @RequestParam(required = false) String classNumber){
        ModelAndView mv = new ModelAndView();
        //获取该年级最近的考试列表
        List<Exam> exams = teacherService.selectRecentlyExams(startYear);
        //该年级的所有班级
        List<String> gradeClass = teacherService.getGradeClass(startYear);
        List<Map<String, String>> grades = teacherService.selectClassSingleSubjectChange(startYear, subject, classNumber);
        //考试的成绩
        JSONArray jsongrades = JSONArray.parseArray(JSON.toJSONString(grades));
        //考试名
        JSONArray examJson = new JSONArray(Collections.singletonList(exams));
        //入学年份
        mv.addObject("startYear", startYear);
        //各班单科成绩
        mv.addObject("jsongrades", jsongrades);
        //考试名
        mv.addObject("exams", examJson);
        //当前年级的所有班级
        mv.addObject("gradeClass", gradeClass);
        //当前班级
        mv.addObject("classNumber", classNumber);
        //所选科目
        mv.addObject("subject", subject);
        mv.setViewName("teacher/examAnalysis");
        return mv;
    }

    /**
     * 跳转到教师端首页
     * @return
     */
    @RequestMapping("goTeacherIndex")
    @Operation(name="访问了教师端首页")
    public String goTeacherIndex(){
        return "teacher/teacherIndex";
    }

    /**
     * 跳转到验证密码页面
     * @return
     */
    @Operation(name="跳转到验证密码页面")
    @RequestMapping("goVerifyPwd")
    public String goVerifyPwd(){
        return "teacher/verifyPwd";
    }

    /**
     * 验证原密码
     * @param request
     * @param pwd 原密码
     * @return
     */
    @RequestMapping("verifyPwd")
    public ModelAndView verifyPwd(HttpServletRequest request, String pwd){
        ModelAndView mv = new ModelAndView();
        String username = (String) request.getSession().getAttribute("username");
        String selectPassword = teacherService.selectPassword(username);
        //进行两次MD5加密
        String inputPassword = MD5Utils.inputPassToDbPass(pwd, MD5Utils.salt);
        if (inputPassword.equals(selectPassword)){
            mv.setViewName("teacher/changePassword");
        }else {
            mv.addObject("msg", "对不起，原密码输入错误！");
            mv.setViewName("teacher/verifyPwd");
        }
        return mv;
    }

    /**
     * 修改密码
     * @param request
     * @param pwd
     * @return
     */
    @Operation(name="修改密码了密码")
    @RequestMapping("changePassword")
    public ModelAndView changePassword(HttpServletRequest request,@RequestParam("newPassword") String pwd){
        ModelAndView mv = new ModelAndView();
        String username = (String) request.getSession().getAttribute("username");
        //进行两次MD5加密
        String inputPassword = MD5Utils.inputPassToDbPass(pwd, MD5Utils.salt);
        Integer result = teacherService.changePassword(username, inputPassword);
        if (result > 0){
            mv.addObject("msg", "密码修改成功！");
        }else {
            mv.addObject("msg", "密码修改出错！");
        }
        return mv;
    }

    /**
     * 跳转到忘记密码页面
     * @return
     */
    @Operation(name="访问忘记密码页面")
    @RequestMapping("goForgetPwd")
    public String goForgetPwd(){
        return "teacher/forgetPwd";
    }

    /**
     * 向预存邮箱发送验证码
     * @param request
     * @return
     */
    @Operation(name="向预存邮箱发送验证码")
    @RequestMapping("sendMail")
    public ModelAndView sendMail(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        String username = (String) request.getSession().getAttribute("username");
        //该用户的邮箱
        String email = teacherService.selectEmail(username);
        //验证码
        String code = CommonUtils.randomCode();
        //邮件正文
        String content = CommonUtils.preContent + code + CommonUtils.rearContent;
        mailService.sendSimpleMail(email, CommonUtils.subject, content);
        //将验证码加入redis
        redisService.setVerifyCode(username, code);
        String key = "code::" + username;
        //设置过期时间
        redisService.setExpire(key);
        mv.setViewName("teacher/forgetPwd");
        mv.addObject("msg", "邮件发送成功！");
        return mv;
    }

    /**
     * 验证验证码
     * @param request
     * @param code
     * @return
     */
    @Operation(name="验证验证码")
    @RequestMapping("verifyCode")
    public ModelAndView verifyCode(HttpServletRequest request, @RequestParam("pwd") String code){
        log.trace("这是trace日志...");
        ModelAndView mv = new ModelAndView();
        String username = (String) request.getSession().getAttribute("username");
        String key = "code::" + username;
        String redisCode = redisService.getVerifyCode(key);
        System.out.println("time = " + redisService.getExpire(key));
        if (code.equals(redisCode)){
            mv.setViewName("teacher/changePassword");
        }else {
            mv.setViewName("teacher/forgetPwd");
            mv.addObject("msg", "验证码错误！");
        }
        return mv;
    }

}
