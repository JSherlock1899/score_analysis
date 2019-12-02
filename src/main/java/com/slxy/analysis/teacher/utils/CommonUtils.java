package com.slxy.analysis.teacher.utils;

import java.util.Random;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/11/13 15:39
 */
public class CommonUtils {

    public static final String subject = "学生成绩分析系统";
    public static final String preContent = "您正在修改密码，您的验证码为：";
    public static final String rearContent = "。此验证码30分钟有效（若非本人操作，请删除本邮件）";
    /**
     * 随机生成六位验证码
     * @return
     */
    public static String randomCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

}
