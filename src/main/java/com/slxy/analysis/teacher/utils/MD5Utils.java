package com.slxy.analysis.teacher.utils;

/**
 * @program: miaosha
 * @description:
 * @author: Mr.Jiang
 * @create: 2019-07-24 14:22
 **/

public class MD5Utils {

    public static final String salt = "1a2b3c4d";

    public static String md5(String src){
        return org.apache.commons.codec.digest.DigestUtils.md5Hex(src);
    }

    public static String inputPassToFromPass(String inputPass){
        String s = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(s);
    }

    public static String FromPassToDBPass(String fromPass,String salt){
        String s = "" + salt.charAt(0) + salt.charAt(2) + fromPass + salt.charAt(5) + salt.charAt(4);
        return md5(s);
    }

    public static String inputPassToDbPass(String input,String saltDB){
        String fromPass = inputPassToFromPass(input);
        String dbPass = FromPassToDBPass(fromPass,saltDB);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassToFromPass("123456"));//3457ceaeba3426466887369f1a1f7a88
        System.out.println(inputPassToDbPass("123","1a2b3c4d"));
    }
}
