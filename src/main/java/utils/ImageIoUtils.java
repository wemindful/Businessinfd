package utils;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

public class ImageIoUtils {
    static{
        //System.load(Core.NATIVE_LIBRARY_NAME);
        String path=System.getProperty("user.dir")+ "\\opencv\\x64\\opencv_java341.dll";
        System.load(path);
    }
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
     * @param srcPath
     * @param toPath
     * @param dstFormat
     * @return
     * @throws IOException
     */
    public static boolean convertImageFormat(File srcPath, File toPath, String dstFormat) throws IOException {
        /*//BufferedInputStream stream = new BufferedInputStream(inputStream);
        FileOutputStream outputStream = new FileOutputStream(toPath+File.separator+dstName+"."+dstFormat);
        BufferedImage image = ImageIO.read(new File(srcPath));
        ImageIO.write(image,srcFomat,outputStream);
        outputStream.flush();*/
        //BufferedImage image = ImageIO.read(new File(srcPath));
        /*if(!ImageIO.write(image, dstFormat, new File(toPath+File.separator+dstName+"."+dstFormat))){
            BufferedImage img = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
            img.getGraphics().drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
            return ImageIO.write(img, dstFormat, new File(toPath+File.separator+dstName+"."+dstFormat));
        }
        return true;*/

        BufferedImage bufferedImage;
        try {
            /*String pathName = srcPath.getName();
            String s = pathName.substring(pathName.lastIndexOf("."), pathName.length());
            if (s.equals(".xlsx"))return false;*/
            bufferedImage = ImageIO.read(srcPath);
            BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
            // write to jpeg file
            ImageIO.write(newBufferedImage, dstFormat, toPath);
        }catch (IOException e){
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Mat转换成BufferedImage
     *
     * @param matrix
     *            要转换的Mat
     * @param fileExtension
     *            格式为 ".jpg", ".png", etc
     * @return
     */
    public static BufferedImage Mat2BufImg (Mat matrix, String fileExtension) {
        // convert the matrix into a matrix of bytes appropriate for
        // this file extension
        MatOfByte mob = new MatOfByte();
        Imgcodecs.imencode(fileExtension, matrix, mob);
        // convert the "matrix of bytes" into a byte array
        byte[] byteArray = mob.toArray();
        BufferedImage bufImage = null;
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bufImage;
    }

    public static void cvtColor(Mat srcmat,Mat destmat){
        Imgproc.cvtColor(srcmat, destmat, Imgproc.COLOR_RGB2GRAY);
    }
    public static void threshold(Mat srcmat,Mat destmat){
        //而二值化处理
        Imgproc.threshold(srcmat, destmat, 150, 255, Imgproc.THRESH_BINARY);
    }
    public static void erode(Mat srcmat,Mat destmat){
        //定义腐蚀块的大小
        Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2, 2));
        Imgproc.erode(srcmat,destmat,element);
    }

}
