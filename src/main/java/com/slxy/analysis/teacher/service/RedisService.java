package com.slxy.analysis.teacher.service;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/11/14 1:02
 */
public interface RedisService {

    void setVerifyCode(String username,String code);

    String getVerifyCode(String key);

    void setExpire(String key);

    long getExpire(String key);
}
