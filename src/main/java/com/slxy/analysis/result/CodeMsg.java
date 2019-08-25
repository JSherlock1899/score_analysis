package com.slxy.analysis.result;

/**
 * @program: score_analysis
 * @description: 返回系统提示信息,按模块划分
 * @author: Mr.Jiang
 * @create: 2019-08-25 18:10
 **/

public class CodeMsg {

    private int code;
    private String msg;

    public static CodeMsg SUCCESS = new CodeMsg(200000,"操作成功");
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
