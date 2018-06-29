package utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @program: businessinformationdiscern
 * @description: 文件后缀相关工具类
 * @author: Mr.Dai
 * @create: 2018-06-28 22:25
 **/
public class FileSuffixUtils {

    /**
     * 清除非图片文件,并且按照数字顺序命名文件
     *
     * @param path
     * @return
     */
    public static boolean clearIllegalFile(String path) {
        randomFileName(path);
      //  System.out.println(path+File.separator+"天猫工商信息执照");
        File file = new File(path+File.separator+"天猫工商信息执照");
        System.out.println(file.getPath());
        for (File file1 : file.listFiles()) {
            String filename = FilenameUtils.getExtension(file1.getPath());
            if(filename!=null&&!filename.equals("png")){
                try {
                    FileUtils.forceDelete(file1);
                } catch (IOException e) {
                    e.printStackTrace();
                    Logger.getLogger("FileSuffixUtils").log(Level.SEVERE,"删除失败");
                }
            }
        }
        int fileCode=0;
        for (File file2 : new File(path+File.separator+"天猫工商信息执照").listFiles()) {
            File f=new File(path+File.separator+"天猫工商信息执照"+File.separator+fileCode+".png");
            file2.renameTo(f);
            //System.out.println(new File(path+File.separator+"天猫工商信息执照"+File.separator+fileCode+".png").getPath());
           // System.out.println(new File(path+"/"+fileCode+".png"));
            ++fileCode;
        }
        return true;
    }

    /**
     *  随机命名解决java renameTo 方法缺陷
     * @param path 路径
     */
    private static void randomFileName(String path){
        File file = new File(path+File.separator+"天猫工商信息执照");
        for (File file1 : file.listFiles()) {
            file1.renameTo(new File(path+File.separator+"天猫工商信息执照"+File.separator+Math.round(Math.random()*1000)+".png"));
        }
    }


    /**
     * 去除非法尺寸图片
     * @param path
     * @return
     */
    public static boolean clearIllegalImage(String path){
        File file = new File(path+File.separator+"天猫工商信息执照");
        for (File file1 : file.listFiles()) {
            try {
                BufferedInputStream bis=new BufferedInputStream(new FileInputStream(file1));
                BufferedImage sourceImg =ImageIO.read(bis);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    public static void main(String[] args) {
        //randomFileName("Z:\\");
        clearIllegalFile("Z:\\");
    }

}
