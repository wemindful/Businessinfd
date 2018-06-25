package main;

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
        discernShopImg shopImg = new discernShopImg(path);
        shopImg.StartDiscern();
    }

}
