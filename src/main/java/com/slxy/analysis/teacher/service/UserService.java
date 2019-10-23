package com.slxy.analysis.teacher.service;

import com.slxy.analysis.teacher.model.ClassGrade;
import com.slxy.analysis.teacher.model.Exam;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/8/31 17:18
 */
public interface UserService {

    /**
     * 用户登录逻辑处理，并获取其基本信息
     * @param request
     * @param username 用户名
     * @param password 密码
     * @param role 权限角色
     * @param model
     * @return
     */
    ModelAndView login(HttpServletRequest request, String username, String password, String role, Model model);

    /**
     * 获取考试名
     * @return
     */
    HashSet<Exam> getExam(List<String> grades);

    ClassGrade getClassAverageGrade(String gradeTable,String classNumber);

}
