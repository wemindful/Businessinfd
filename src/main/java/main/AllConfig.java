package main;

import java.io.File;

/**
 * @program: businessinformationdiscern
 * @description: 全局配置文件
 * @author: Mr.Dai
 * @create: 2018-07-19 21:38
 **/
public interface AllConfig {

    /**
     * 启动基础目录
     */
    public final static String basepath=System.getProperty("user.dir");

    /**
     * excel目录设置
     */
    public final static String excel = basepath + File.separator+"工商信息执照.xls";

    /**
     * Sets path to <code>tessdata</code>
     */
    //public final static String tessdata = "D:\\businessinformationdiscern_jar"+"/tessdata";//基础目录
    public final static String tessdata = System.getProperty("user.dir")+File.separator +"tessdata";//基础目录
}
