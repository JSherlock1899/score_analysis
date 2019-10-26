package com.slxy.analysis.teacher.util;

import java.io.*;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/10/23 8:24
 */
public class MySQLUtils {

    /**
     * 数据库表备份
     *
     * @throws Exception
     */
    public static void tableBackup(String dbUser,String dbPass,String dbHost,String dbPort,String dbName,String savePath, String tableName)
            throws Exception {

        Runtime runtime = Runtime.getRuntime();
        // -u后面是用户名，-p是密码-p后面最好不要有空格，-family是数据库的名字
        Process process = runtime.exec("mysqldump -h " + dbHost + " -P "
                + dbPort + " -u " + dbUser + " -p" + dbPass + " " + dbName
                + " " + tableName);
        InputStream inputStream = process.getInputStream();// 得到输入流，写成.sql文件
        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(reader);
        String s = null;
        StringBuffer sb = new StringBuffer();
        while ((s = br.readLine()) != null) {
            sb.append(s + "\r\n");
        }
        s = sb.toString();
        File file = new File(savePath);
        file.getParentFile().mkdirs();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(s.getBytes());
        fileOutputStream.close();
        br.close();
        reader.close();
        inputStream.close();
    }

    /**
     * 备份数据库
     *
     * @throws Exception
     */
    public static void dbBackup(String dbUser, String dbPass, String dbHost, String dbName
                                ) throws Exception {

        Runtime runtime = Runtime.getRuntime();
//        Process process1 = runtime.exec("docker exec -it mysql01 bash");
        Process process = runtime.exec("ls > 1.txt");
//        System.out.println("-------------------------------------" + process1.isAlive());
        // -u后面是用户名，-p是密码-p后面最好不要有空格，-family是数据库的名字
//        Process process2 = runtime.exec("/usr/bin/mysqldump -h " + dbHost + " -P "
//                 + " -u " + dbUser + " -p" + dbPass + " " +  dbName);
//        System.out.println("/usr/bin/mysqldump -h " + dbHost + " -P "
//                + " -u " + dbUser + " -p" + dbPass + " " +  dbName  + " > /home/1111.sql");
    }

    /**
     * 执行sql文件
     *
     * @param savePath
     * @throws Exception
     */
    public static void dbRecover(String dbUser, String dbPass, String dbHost,
                                String dbName, String savePath) throws Exception {
        // 获取操作数据库的相关属性

        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("mysql -h" + dbHost + " -P "
                + " -u " + dbUser + " -p" + dbPass
                + " --default-character-set=utf8 " + dbName);
        OutputStream outputStream = process.getOutputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(savePath)));
        String str = null;
        StringBuffer sb = new StringBuffer();
        while ((str = br.readLine()) != null) {
            sb.append(str + "\r\n");
        }
        str = sb.toString();
        OutputStreamWriter writer = new OutputStreamWriter(outputStream,
                "utf-8");
        writer.write(str);
        writer.flush();
        outputStream.close();
        br.close();
        writer.close();
    }

    public static void main(String[] args) {
        /*try {
            dbBackup("root", "ROOT", "localhost","3306", "EPDB", "D:/back.sql");
            System.out.println("完成！");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
//        try {
//            dbRecover("root", "ROOT", "localhost","3306", "EPDB", "D:/back.sql");
//            System.out.println("完成！");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            dbBackup("root","root","39.107.90.231","score_analysis");
            System.out.println("yeah！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
