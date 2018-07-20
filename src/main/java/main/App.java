package main;

import FormUI.AppUi;
import services.dataProcessing;
import services.discernShopImg;

/**
 * @program: businessinformationdiscern
 * @description: 程序的入口
 * @author: Mr.Dai
 * @create: 2018-06-25 16:35
 **/
public class App {

    private final static String path=System.getProperty("user.dir");
    public static void main(String[] args) {
        System.out.println(args[0]);
        //user 选择执行的方式
        if(args[0].equals("gui")){
            new AppUi(path).setVisible(true);
        }else if(args[0].equals("console")){
            //discernShopImg shopImg = new discernShopImg("D:\\businessinformationdiscern_jar","console");
            //shopImg.StartDiscern();
            dataProcessing pro=new dataProcessing();
            pro.finalProcess();
        }
    }

}
