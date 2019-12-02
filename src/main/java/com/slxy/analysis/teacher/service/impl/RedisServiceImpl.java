package com.slxy.analysis.teacher.service.impl;

import com.slxy.analysis.teacher.service.RedisService;
import com.slxy.analysis.teacher.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/11/14 1:03
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    RedisUtils redisUtils;

    public void setVerifyCode(String username,String code){
        redisUtils.setCode(username,code);
    }

    @Override
    public String getVerifyCode(String key) {
        return redisUtils.getCode(key);
    }

    @Override
    public void setExpire(String key) {
        redisUtils.setExpire(key);
    }

    @Override
    public long getExpire(String key) {
        return redisUtils.getExpire(key);
    }
}
