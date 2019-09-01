package com.slxy.analysis.result;

/**
 * @author: sherlock
 * @description: 按模块划分,返回系统提示信息
 * @date: 2019/8/30 10:32
 */
public class CodeMsg {

    private int code;
    private String msg;

    public static CodeMsg SUCCESS = new CodeMsg(200000,"操作成功");
    //登录异常
    public static CodeMsg USERNAME_ERROR = new CodeMsg(500100,"用户名不存在");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500100,"密码错误");
    //通用异常
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100,"服务器端异常");

    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
