package utils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class ImageIoUtils {


    /**
     * 对图片裁剪，并把裁剪新图片保存
     *
     * @param srcPath          读取源图片路径
     * @param toPath           写入图片路径
     * @param x                剪切起始点x坐标
     * @param y                剪切起始点y坐标
     * @param width            剪切宽度
     * @param height           剪切高度
     * @param readImageFormat  读取图片格式
     * @param writeImageFormat 写入图片格式
     * @throws IOException
     */
    public static void cropImage(String srcPath, String toPath,
                                 int x, int y, int width, int height,
                                 String readImageFormat, String writeImageFormat) throws IOException {
        FileInputStream fis = null;
        ImageInputStream iis = null;
        try {
            //读取图片文件
            fis = new FileInputStream(srcPath);
            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(readImageFormat);
            ImageReader reader = (ImageReader) it.next();
            //获取图片流
            iis = ImageIO.createImageInputStream(fis);
            reader.setInput(iis, true);
            ImageReadParam param = reader.getDefaultReadParam();
            //定义一个矩形
            Rectangle rect = new Rectangle(x, y, width, height);
            //提供一个 BufferedImage，将其用作解码像素数据的目标。
            param.setSourceRegion(rect);
            BufferedImage bi = reader.read(0, param);
            //保存新图片
            ImageIO.write(bi, writeImageFormat, new File(toPath));
        } finally {
            if (fis != null)
                fis.close();
            if (iis != null)
                iis.close();
        }
    }

    /**
     * 转换图片格式
     *
     * @param srcPath
     * @param toPath
     * @param srcFomat
     * @param dstFormat
     */
    public static void convertImageFormat(String srcPath, String toPath, String srcFomat, String dstFormat,String dstName) throws IOException {

        FileInputStream inputStream = new FileInputStream(srcPath);
        FileOutputStream outputStream = new FileOutputStream(toPath+File.separator+dstName+"."+dstFormat);
        BufferedImage image = ImageIO.read(inputStream);
        ImageIO.write(image,srcFomat,outputStream);
    }

}
