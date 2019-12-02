package com.slxy.analysis.teacher.utils;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/10/23 9:03
 */
public class LinuxCmdUtils {

//    static Logger logger= LoggerFactory.getLogger(LinuxCmdUtils.class);

    public  static boolean executeLinuxCmd(String cmd) {

        boolean result=false;

        System.out.println("got cmd : " + cmd);
        Runtime run = Runtime.getRuntime();
        //InputStream in=null;
        try {
            Process process = run.exec(cmd);
            //执行结果 0 表示正常退出
            int exeResult=process.waitFor();
            if(exeResult==0){
                System.out.println("执行成功");
                result=true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
//            logger.error("LinuxCmdUtils.executeLinuxCmd error",e);
        }
        return result;
    }




    public static void main(String[] args) {

//        System.out.println(LinuxCmdUtils.getGrepCmdReturn("dmidecode -s system-serial-number|grep -v \"#\""));
        executeLinuxCmd("docker exec -it mysql01 bash");

    }
}
