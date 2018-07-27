package services;

import main.AllConfig;
import utils.ClearWaterImgesInit;
import utils.FileSuffixUtils;
import utils.ImageIoUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: businessinformationdiscern
 * @description: 图片一系列处理
 * @author: Mr.Dai
 * @create: 2018-07-19 15:30
 **/
public class DiscernALLImg {
    private static String basepath = AllConfig.basepath;
    //private static String basepath = "Z:\\";//基础目录

    /**
     *  转换格式bmp /处理水印
     */
    public  Map<Integer,BufferedImage> imgFixedProcess(){
        initEnvironment();
        File root = new File(DiscernALLImg.basepath + File.separator+"天猫工商信息执照");
        Map<Integer,BufferedImage> convertImgMap=new HashMap<>();
        if (root.isDirectory()) {
            File[] files = root.listFiles();
            for (int i = 0; i < files.length; i++) {
                BufferedImage convertimg=ImageIoUtils.convertImageFormat(files[i],"bmp");
                BufferedImage clearWaterImg=ClearWaterImgesInit.fileToclearWatermark(convertimg);
                //
                BufferedImage  strongImg=ImageIoUtils.erodeToBufferImage(clearWaterImg,".bmp");
                convertImgMap.put(i,strongImg);
            }
        }
        return convertImgMap;
        /*for (int i = 0; i < convertImgMap.size(); i++) {
            try {
                ImageIO.write(convertImgMap.get(i),"bmp",new File("z:/test/"+i+".bmp"));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }*/
    }

    /**
     * 初始化目录环境 //判断缓存目录是否为null 如果是就清空
     */
    public static void initEnvironment(){
        File root = new File(DiscernALLImg.basepath + "/天猫工商信息执照");
        File xlsx = new File(DiscernALLImg.basepath + "/工商信息执照.xls");
        if (!root.exists() || xlsx.exists()) {
            root.mkdir();
            xlsx.delete();
        }
        FileSuffixUtils.clearIllegalFile(basepath);
    }


}
