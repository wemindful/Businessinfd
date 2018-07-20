package utils;

import com.lowagie.text.html.simpleparser.Img;
import net.sourceforge.tess4j.util.ImageIOHelper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import services.discernShopImg;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        File file = new File(path + File.separator + "天猫工商信息执照");
        System.out.println(file.getPath());
        for (File file1 : file.listFiles()) {
            String filename = FilenameUtils.getExtension(file1.getPath());
            if (filename != null && !filename.equals("png")) {
                try {
                    FileUtils.forceDelete(file1);
                } catch (IOException e) {
                    e.printStackTrace();
                    Logger.getLogger("FileSuffixUtils").log(Level.SEVERE, "删除失败");
                }
            }
        }
        clearNotEagleImg(file.getAbsolutePath());
        File[] files = new File(path + File.separator + "天猫工商信息执照").listFiles();
        ArrayList<File> filelist = new ArrayList<>();
        for (File fileto : files) {
            filelist.add(fileto);
        }
        Collections.sort(filelist, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.isDirectory() && o2.isFile())
                    return -1;
                if (o1.isFile() && o2.isDirectory())
                    return 1;
                Integer f = FileSuffixUtils.fequals(o1.getName());
                Integer f2 = FileSuffixUtils.fequals(o2.getName());
                return Integer.compare(f, f2);
            }
        });
        int fileCode = 1;
        for (File file2 : filelist) {
            File f = new File(path + File.separator + "天猫工商信息执照" + File.separator + fileCode + ".png");
            file2.renameTo(f);
            fileCode++;
        }
        return true;
    }

    /**
     * 随机命名解决java renameTo 方法缺陷
     *
     * @param path 路径
     */
    private static void randomFileName(String path) {
        File file = new File(path + File.separator + "天猫工商信息执照");
        for (File file1 : file.listFiles()) {
            file1.renameTo(new File(path + File.separator + "天猫工商信息执照" + File.separator + Math.round(Math.random() * 1000) + ".png"));
        }
    }


    /**
     * 去除非法尺寸图片
     *
     * @param path
     * @return
     */
    public static boolean clearIllegalImage(String path) {
        File file = new File(path + File.separator + "天猫工商信息执照");
        for (File file1 : file.listFiles()) {
            try {
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file1));
                BufferedImage sourceImg = ImageIO.read(bis);
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

    /**
     * 当宽不等于1200 直接删除
     * @throws IOException
     */
    private static void clearNotEagleImg(String filepath) {
        File path = new File(filepath);
        for (File file : path.listFiles()) {
            BufferedImage img = null;
            try {
                img = ImageIO.read(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (img.getWidth() != 1200) {
                file.delete();
            }
        }
    }

        /**
         * 按照指定办法读取文件
         *
         * @param filename
         * @return
         */
        public static Integer fequals (String filename){
            int x = filename.indexOf(".");
            String string2 = filename.substring(0, x);
            char[] cs = string2.toCharArray();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < cs.length; i++) {
                if (Character.isDigit(cs[i])) {
                    builder.append(cs[i]);
                }
            }
            return Integer.parseInt(builder.toString());
        }
    }
