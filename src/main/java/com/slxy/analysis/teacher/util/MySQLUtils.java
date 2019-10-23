package com.slxy.analysis.teacher.util;

import java.io.IOException;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/10/23 8:24
 */
public class MySQLUtils {
//    public static boolean backup(String username,String password,String dbName,String mysqldumpPath, String backupPath) {
//        boolean status = false;
//
//        String command = mysqldumpPath + "/mysqldump -u " + username + " -p" + password + " " + dbName + " -r " + backupPath;
//        try {
//            Process runtimeProcess = Runtime.getRuntime().exec(command);
//            int processComplete = runtimeProcess.waitFor();
//            if (processComplete == 0) {
//                System.out.println("MySQLManager: Backup database Successfull");
//                status = true;
//            } else {
//                System.out.println("MySQLManager: Backup database Failure!");
//            }
//        } catch (IOException ioe) {
//            System.out.println("Exception IO");
//            ioe.printStackTrace();
//        } catch (Exception e) {
//            System.out.println("Exception");
//            e.printStackTrace();
//        }
//        return status;
//    }
    public static boolean backup(String username,String password,String dbName,String mysqldumpPath, String backupPath) {
        boolean status = false;

        String[] command = new String[]{"/usr/bin/mysqldump -h39.107.90.231 -u root -proot score_analysis > /home/backu00p.sql"};

        try {
            Process runtimeProcess = Runtime.getRuntime().exec(command);
            int processComplete = runtimeProcess.waitFor();
            if (processComplete == 0) {
                System.out.println("MySQLManager:Restore database Successfull");
                status = true;
            } else {
                System.out.println("MySQLManager:Restore database Failure");
            }
        } catch (IOException ioe) {
            System.out.println("Exception IO");
            ioe.printStackTrace();
        } catch (Exception e) {
            System.out.println("Exception");
            e.printStackTrace();
        }
        return status;
    }
    public static boolean restore(String username,String password,String filePath){
        boolean status = false;

        String[] command = new String[]{"/usr/local/mysql/bin/mysql", "exp_transactions", "-u" + username, "-p" + password, "-e", " source "+filePath };

        try {
            Process runtimeProcess = Runtime.getRuntime().exec(command);
            int processComplete = runtimeProcess.waitFor();
            if (processComplete == 0) {
                System.out.println("MySQLManager:Restore database Successfull");
                status = true;
            } else {
                System.out.println("MySQLManager:Restore database Failure");
            }
        } catch (IOException ioe) {
            System.out.println("Exception IO");
            ioe.printStackTrace();
        } catch (Exception e) {
            System.out.println("Exception");
            e.printStackTrace();
        }
        return status;
    }

    //for testing
    public static void main(String args[]){
        //String backupName = "D:/DatabaseBackup/backupHvs.sql";
        //MySQLManager.restore(backupName);
        MySQLUtils.backup("root","jyh05745@","score_analysis","/usr/local/mysql/bin","/Users/jeff/Movies/backup_20120419_101741.sql");
        //MySQLManager.restore("root","123","/Users/jeff/Movies/backup_20120419_101741.sql");
    }

}
