package utils;

import sun.applet.Main;

/**
 * @program: businessinformationdiscern
 * @description: 系统环境设置
 * @author: Mr.Dai
 * @create: 2018-07-26 23:21
 **/
public class SystemSetting {


    public static String loadLib(){
        String path64=System.getProperty("user.dir")+ "\\opencv\\x64\\opencv_java341.dll";
        String path86=System.getProperty("user.dir")+ "\\opencv\\x86\\opencv_java341.dll";
        String version=System.getProperty("os.arch");
        if(version.contains("64")){
            //System.out.println("yes");
            return  path64;
        }else{
            //System.out.println("86");
            return  path86;
        }

    }

    public static void main(String[] args) {
        loadLib();
    }
}
