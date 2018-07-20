import org.apache.commons.io.FileUtils;
import org.junit.Test;
import services.discernShopImg;
import utils.ClearWaterImgesInit;
import utils.FileSuffixUtils;
import utils.ImageIoUtils;
import utils.OpenCvUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: businessinformationdiscern
 * @description: 全局识别处理
 * @author: Mr.Dai
 * @create: 2018-07-19 14:20
 **/
public class TestDiscernALLImg {

    //private static String basepath = System.getProperty("user.dir");//基础目录
    private static String basepath = "Z:\\";//基础目录

    /**
     *         //转换格式bmp /处理水印
     */
    @Test
    public  void imgFixedProcess(){
        initEnvironment();
        File root = new File(TestDiscernALLImg.basepath + "/天猫工商信息执照");
        Map<Integer,BufferedImage> convertImgMap=new HashMap<>();
        if (root.isDirectory()) {
            File[] files = root.listFiles();
            for (int i = 0; i < files.length; i++) {
                BufferedImage convertimg=ImageIoUtils.convertImageFormat(files[i],"bmp");
                BufferedImage clearWaterImg=ClearWaterImgesInit.fileToclearWatermark(convertimg);
                convertImgMap.put(i,clearWaterImg);
            }
        }
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
        File root = new File(TestDiscernALLImg.basepath + "/天猫工商信息执照");
        File xlsx = new File(TestDiscernALLImg.basepath + "/工商信息执照.xls");
        if (!root.exists() || xlsx.exists()) {
            root.mkdir();
            xlsx.delete();
        }
        FileSuffixUtils.clearIllegalFile(basepath);
    }

}
