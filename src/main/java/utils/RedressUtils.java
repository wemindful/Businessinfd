package utils;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: businessinformationdiscern
 * @description: 数据矫正工具类
 * @author: Mr.Dai
 * @create: 2018-06-29 17:48
 **/
public class RedressUtils {

    /**
     *  如果出现不是数字字母则不需要处理  ：后期将单独写算法 将图片旋转 识别 完善
     * @param name
     * @return
     */
    public static boolean digitalRedress(String name){

        Pattern pattern= Pattern.compile("^[0-9a-zA_Z]+$");
        Matcher matcher= pattern.matcher(name);
        if(!matcher.matches()){
           return true;
        }
       return false;
    }

    /**
     * 如果出现不是汉字则不需要处理  ：后期将单独写算法 将图片旋转 识别 完善
     * @param name  企业名称可能出现（ ） 这个正则不合适 并且速度慢
     * @return
     */
    public static boolean characterRedress(String name){

        Pattern pattern= Pattern.compile("^[\\u4e00-\\u9fa5\\\\w]+$");
        Matcher matcher= pattern.matcher(name);
        if(matcher.matches()){
            return true;
        }
        return false;
    }

}
