package com.slxy.analysis.teacher.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.slxy.analysis.teacher.mapper.TeacherMapper;
import com.slxy.analysis.teacher.model.ClassGrade;
import com.slxy.analysis.teacher.model.Exam;
import com.slxy.analysis.teacher.model.Grade;
import com.slxy.analysis.teacher.model.Teacher;
import com.slxy.analysis.teacher.service.TeacherService;
import com.slxy.analysis.teacher.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/9/1 11:06
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    UserService userService;

    @Override
    @Cacheable(cacheNames = "teacher")
    public Teacher getTeacherById(String id) {
        return teacherMapper.getTeacherById(id);
    }

    @Override
    @Cacheable(cacheNames = "teacherRole")
    public String getTeacherRole(String id) {
        return teacherMapper.getTeacherRole(id);
    }

    @Override
    @Cacheable(cacheNames = "teacherNameAndSubject")
    public Teacher getTeacherNameAndSubject(String id) {
        return teacherMapper.getTeacherNameAndSubject(id);
    }

    @Override
    @Cacheable(cacheNames = "teacherClass")
    public List<String> getTeacherClass(String subject, String id){
        return teacherMapper.getTeacherClass(subject, id);
    }

    /**
     * 获取学生成绩列表
     * @param exam
     * @param classNumber
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<Grade> getStudentGrades(String exam, String classNumber,Integer pageNum, Integer pageSize,String subject,String sort) {
        //获取班级所处年级
        if (classNumber != null) {
            String grade = classNumber.substring(0, 2);
            subject = subject + "_grades";
            return teacherMapper.getStudentGrades(exam, classNumber, grade,pageNum,pageSize,subject,sort);
        }
        return null;
    }


    /**
     * 获取该教师所教的班级列表
     * @param request
     * @return
     */
    @Override
    public List<String> listClass(HttpServletRequest request){
        //从session中获取username
        String username = (String) request.getSession().getAttribute("username");
        //获取教师所教的课程
        Teacher teacher = getTeacherNameAndSubject(username);
        //根据教师所教的课程和工号获取教师所教的班级
        List<String> classList = getTeacherClass(teacher.getSubject(), username);
        return classList;
    }

    /**
     *获取各班级的成绩
     */
    @Override
    @Cacheable("ClassGrades")
    public List<ClassGrade> getClassesGrades(String classGradeTable, String startYear, Integer pageNum, Integer pageSize,String subject, String sort) {
        subject = subject + "_average_grades";
        return teacherMapper.getClassesGrades(classGradeTable, startYear, pageNum, pageSize, subject, sort);
    }

    /**
     * 获取某个年级有哪几个班
     * @param startYear 入学年份，也可以看做是年级
     * @return
     */
    @Override
    public List<String> getGradeClass(String startYear) {
        return teacherMapper.getGradeClass(startYear);
    }

    /**
     * 动态查询教师所教的年级（一个教师可能教多个年级）
     * @param classes 教师所教的班级列表
     * @return
     */
    @Override
    public List<String> getTeacherGrade(List<String> classes) {
        String s;
        List<String> grade = new ArrayList<String>();
        for (String c : classes){
            //分割班级字符串，得到前两位数，也就是对应的年级
            s = c.substring(0, 2);
            if (!grade.contains(s)){
                grade.add(s);
            }
        }
        return grade;
    }

    /**
     * 获取每个年级的考试列表
     * @param grade
     * @return
     */
    @Override
    public List<Exam> getExamList(String grade) {
        return teacherMapper.getExamList(grade);
    }

    /**
     * 获取某班某一次成绩的列表
     * @param examTable
     * @param classNumber
     * @return
     */
    @Override
    public Grade getClassGrades(String examTable, String classNumber) {
        //获取班级成绩的表名
        examTable = examTable.replace("students", "class");
        return teacherMapper.getClassGrades(examTable,classNumber);
    }

    /**
     * 获取各次某班成绩的列表
     * @param classNumber 班级
     * @return
     */
    @Override
    public List<Grade> getClassGradeList(String classNumber){
        String grade = classNumber.substring(0, 2);
        //获取该班级已经进行的考试
        List<Exam> examList = getExamList(grade);
        List<Grade> grades = new ArrayList<Grade>();
        //逐个查询出该班级各次考试的成绩对象并添加到列表中
        for (Exam exam : examList){
            //获取单次考试的成绩对象
            Grade classGrades = getClassGrades(exam.getTableName(), classNumber);
            if (classGrades != null){
                //设置考试名
                classGrades.setName(getExamName(exam.getTableName()));
            }
            grades.add(classGrades);
        }
        return grades;
    }

    /**
     * 根据考试表获取考试名
     * @param examTable 考试表
     * @return
     */
    @Override
    public String getExamName(String examTable) {
        return teacherMapper.getExamName(examTable);
    }

    /**
     * 查询全校前num名的学生人数
     * @param examTable 考试表
     * @param classNumber 班级
     * @return
     */
    @Override
    public Integer getTopStudnetNumber(String examTable, String classNumber, Integer num) {
        String rankingTable = examTable.replace("grades", "ranking");
        Integer studnetNumber = teacherMapper.getTopStudnetNumber(rankingTable, classNumber,num);
        return studnetNumber;
    }

    /**
     * 查询某班学生人数
     * @param classNumber
     * @return
     */
    @Override
    public Integer getClassStudentNumber(String classNumber) {
        return teacherMapper.getClassStudentNumber(classNumber);
    }

    /**
     * 获取某班学生在某次考试中的排名分布
     * @param examTable 考试表
     * @param classNumber 班级
     * @return
     */
    public List<Map<String, String>> getStudnetRankingDistribute(String examTable, String classNumber){
        //获取前100名学生人数
        Integer topHundredStudnetNumber = getTopStudnetNumber(examTable, classNumber, 100);
        //获取前200名学生人数
        Integer topTwoHundredStudnetNumber = getTopStudnetNumber(examTable, classNumber, 200);
        //获取前500名学生人数
        Integer topFiveHundredStudnetNumber = getTopStudnetNumber(examTable, classNumber, 500);
        //获取该班级的学生人数
        Integer classStudentNumber = teacherMapper.getClassStudentNumber(classNumber);
        //获取剩余学生人数
        Integer restStudnet = classStudentNumber  - topFiveHundredStudnetNumber;
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("type", "前100名");
        map1.put("number", topHundredStudnetNumber.toString());
        list.add(map1);
        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("type", "100～200名");
        map2.put("number", String.valueOf(topTwoHundredStudnetNumber - topHundredStudnetNumber));
        list.add(map2);
        Map<String, String> map3 = new HashMap<String, String>();
        map3.put("type", "200～500名");
        map3.put("number", String.valueOf(topFiveHundredStudnetNumber - topTwoHundredStudnetNumber));
        list.add(map3);
        Map<String, String> map4 = new HashMap<String, String>();
        map4.put("type", "500名以后");
        map4.put("number", restStudnet.toString());
        list.add(map4);
        return list;
    }

    /**
     * 查询教师备注信息
     * @param id 教师工号
     * @return
     */
    @Override
    public String getTeacherRemark(String id) {
        return teacherMapper.getTeacherRemark(id);
    }

    @Override
    public List<String> getPresentGrade() {
        return teacherMapper.getPresentGrade();
    }

    /**
     *
     * @param request
     * @param role 权限角色
     * @param remark 教师的备注
     * @param adminClass 管理员级别初始化查询对应的班级
     * @param startYear 入学年份
     * @return
     */
    @Override
    public List<String> getClassList(HttpServletRequest request, String role, String remark, String adminClass, String startYear){
        List<String> classList = new ArrayList<>();
        if ("2".equals(role) || "3".equals(role)){
            //初始化老师所带的班级的下拉框
            classList = listClass(request);
        }else if("4".equals(role)){
            //获取该教师是哪个年级的年级组长
            String grade = remark.substring(0, 2);
            //获取该年级有哪几个班
            classList = getGradeClass(grade);
        }else if ("5".equals(role) || "6".equals(role)){
            if (startYear == null){
                //获取所选年级有哪几个班
                classList = getGradeClass(adminClass);
            }else{
                //获取所选年级有哪几个班
                classList = getGradeClass(startYear);
            }
        }
        return classList;
    }

    @Override
    public List<Map<String, Integer>> selectPassLineStudents(String startYear, String exam, String cutOffGrade){
        if (exam == null){
            //获取该年级最近的一次考试
        }
        List<String> classList = getGradeClass(startYear);
        Integer cutOffGradeInt = Integer.parseInt(cutOffGrade);
        List<Map<String, Integer>> list = new ArrayList<>();
        //遍历各班级，分别获取各班的过线人数
        for (String c : classList){
            Map<String, Integer> map = new HashMap<>();
            map.put("classNumber", Integer.valueOf(c));
            map.put("number", teacherMapper.selectPassLineStudents(c, exam, cutOffGradeInt));
            list.add(map);
        }
        return list;
    }

    @Override
    public String selectTeacherSubject(String id) {
        return teacherMapper.selectTeacherSubject(id);
    }

    @Override
    public List disposeList(List list){
        if(list.size() > 5){
            for (int i = 0; i < list.size() - 5; i++ ){
                list.remove(i);
            }
        }
        return list;
    }

    /**
     * 查询各班单科的成绩变化
     * @param grade 年级
     * @param subject 科目
     * @return
     */
    public List<Map<String, String>> selectClassSingleSubjectChange(String grade, String subject, String classNumber){
        //分别处理grade和subject和classNumber
        if (grade == null){
            grade = teacherMapper.getPresentGrade().get(1);
        }
        subject = subject + "_average_grades";
        if (classNumber == null){
            classNumber = teacherMapper.getGradeClass(grade).get(0);
        }
        //获取该年级最近的考试列表
        List<Exam> exams = teacherMapper.selectRecentlyExams(grade);
        exams = disposeList(exams);
        List<Map<String, String>> classGrades = new ArrayList<>();
        for (int i = 0; i < exams.size(); i++) {

            List<ClassGrade> grades = teacherMapper.selectSingleSubjectGrades(subject, exams.get(i).getTableName().replace("students", "class"),classNumber);
            for (int j = 0; j < grades.size(); j++) {
                HashMap<String, String> map = new HashMap();
                String correspondingGrade = getCorrespondingGrade(grades.get(j), subject);
                map.put("grade", correspondingGrade);
                classGrades.add(map);
            }

        }
        return classGrades;
    }

    @Override
    public List<Exam> selectRecentlyExams(String grade) {
        return teacherMapper.selectRecentlyExams(grade);
    }

    @Override
    public ModelAndView setCutOffGrade(HttpServletRequest request, String startYear) {
        ModelAndView mv = new ModelAndView();
        //首次查询时初始化年级
        List<String> presentGrade = getGradeList(request);
        //初始化考试名称的下拉框
        HashSet<Exam> examList = userService.getExam(Collections.singletonList(startYear));
        //年级
        mv.addObject("presentGrade", presentGrade);
        //考试列表
        mv.addObject("examList", examList);
        return mv;
    }

    /**
     * 处理教师所在年级（入学年份）的字符串
     * @param request
     * @param startYear
     * @return
     */
    @Override
    public String disposeStartYear(HttpServletRequest request,String startYear){
        if (startYear == null){
            //获取教师所教的班级列表
            List<String> listClass = listClass(request);
            //此处暂时默认一个教师只教一个年级
            startYear = listClass.get(0).substring(0, 2);
        }
        return startYear;
    }

    public String getCorrespondingGrade(ClassGrade grades, String subject) {
        String grade = "";
        switch (subject) {
            case "chinese_average_grades": grade = grades.getChineseAverageGrades();break;
            case "math_average_grades": grade = grades.getMathAverageGrades();break;
            case "english_average_grades": grade = grades.getEnglishAverageGrades();break;
            case "physics_average_grades": grade = grades.getPhysicsAverageGrades();break;
            case "chemistry_average_grades": grade = grades.getChemistryAverageGrades();break;
            case "biology_average_grades": grade = grades.getBiologyAverageGrades();break;
            case "politics_average_grades": grade = grades.getPoliticsAverageGrades();break;
            case "history_average_grades": grade = grades.getHistoryAverageGrades();break;
            case "geography_average_grades": grade = grades.getGeographyAverageGrades();break;
            case "technology_average_grades": grade = grades.getTechnologyAverageGrades();break;
            case "total_point_average_grades": grade = grades.getTotalPointAverageGrades();break;
        }
        return grade;
    }

    /**
     * 对考试值进行初始化
     * @param exam
     * @param examList
     * @return
     */
   public String disposeExam(String exam,List<Exam> examList){
        if (exam == null){
            return examList.get(0).getTableName();
        }else{
            return exam;
        }
   }

    /**
     * 初始化年级列表
     * @param request
     * @return
     */
    @Override
   public List<String> getGradeList(HttpServletRequest request){
       String  username = (String) request.getSession().getAttribute("username");
       String role = getTeacherRole(username);
       if ("5".equals(role) || "6".equals(role)){
            return getPresentGrade();
       }else {
           List<String> listClass = listClass(request);
           return Arrays.asList(listClass.get(0).substring(0, 2));
       }
   }

    /**
     * 计算过线总人数
     * @param examTable
     * @param cutOffGrade
     * @return
     */
    @Override
    public Integer calcPassLineCount(String examTable, Integer cutOffGrade) {
        return teacherMapper.calcPassLineCount(examTable, cutOffGrade);
    }

    /**
     * 获取各班指定排名段的人数分布
     * @param examTable 考试表
     * @param grade 年级
     * @param ranking 指定排名段
     * @param subject 学科
     * @return
     */
    @Override
    public ModelAndView selectClassesRanking(HttpServletRequest request, String examTable, String grade, String ranking, String subject) {
        ModelAndView mv = new ModelAndView();
        //初始化年级值
        if (grade == null || "".equals(grade)){
            //查询现在所有的年级
            List<String> gradeList = getGradeList(request);
            //初始化年级字段
            grade = gradeList.get(0);
            mv.addObject("grade", grade);
        }else {
            mv.addObject("grade", grade);
        }
        //初始化考试表值
        System.out.println("fg = " + grade);
        if (examTable == null || "".equals(examTable)){
            examTable = getExamList(grade).get(0).getTableName();
            mv.addObject("exam", examTable);
        }else {
            mv.addObject("exam", examTable);
        }
        //查询该年级的班级集合
        List<String> gradeClass = getGradeClass(grade);
        //根据数据库处理examTable和subject字段
        String rankingTable = examTable.replace("grades", "ranking");
        subject = subject + "_school_ranking";
        List<Map<String, Integer>> list = new ArrayList<>();
        //遍历各班级，获取各班指定排名段的人数分布
        for (String c : gradeClass){
            Map<String, Integer> map = new HashMap<>();
            map.put("classNumber", Integer.valueOf(c));
            map.put("number", teacherMapper.selectClassesRanking(rankingTable, c, ranking, subject));
            list.add(map);
        }
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(list));
        //要显示的考试列表
        List<Exam> examList = getExamList(grade);
        //各班该分段人数列表
        mv.addObject("list", list);
        //用于绘制统计图
        mv.addObject("jsonArray", jsonArray);
        //分段范围
        mv.addObject("ranking", ranking);
        //考试列表
        mv.addObject("examList", examList);
        return mv;
    }

}
