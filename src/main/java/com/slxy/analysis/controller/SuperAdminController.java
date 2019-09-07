package com.slxy.analysis.controller;

import com.slxy.analysis.service.SuperAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

    /**
     * 创建各考试用表
     * @return
     */
    @RequestMapping("createTable")
    public ModelAndView createTable(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        try{
            superAdminService.createStudentTable("18_students_grades_20190418");
        }catch (Exception e){
            mv.addObject("msg", "表已存在");
            return mv;
        }
        mv.addObject("msg", "建表成功");
        return mv;
    }
}
