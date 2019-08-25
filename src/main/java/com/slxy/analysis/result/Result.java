package com.slxy.analysis.result;

/**
 * @program: score_analysis
 * @description: 返回相应的对象
 * @author: Mr.Jiang
 * @create: 2019-08-25 21:13
 **/

public class Result<T> {

    private int code;
    private String msg;
    private T data;

    private Result(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    //CodeMsg包含了code和msg
    public Result(CodeMsg codeMsg) {
        if (codeMsg == null){
            return;
        }
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
    }

    //成功时的调用    
    public static <T> Result<T> success(T data){
        return new Result<T>(data);
    }

    //失败时的调用
    public static <T> Result<T> error(CodeMsg codeMsg){
        return new Result<T>(codeMsg);
    }
    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}