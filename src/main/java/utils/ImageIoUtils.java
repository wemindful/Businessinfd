package utils;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImageIoUtils {

        static{
            System.load(SystemSetting.loadLib());
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
            ImageReader reader = it.next();
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
     * 对图片裁剪，并把裁剪新图片保存为一个list
     *
     * @param images           读取源图片路径
     * @param x                剪切起始点x坐标
     * @param y                剪切起始点y坐标
     * @param width            剪切宽度
     * @param height           剪切高度
     * @param readImageFormat  读取图片格式
     * @return buffer           剪切后的数据
     * @throws IOException
     */
    public static ArrayList<BufferedImage> cropImageForList(File[] images,
                                        int x, int y, int width, int height,
                                        String readImageFormat) {

        ArrayList<BufferedImage> buffer=new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            FileInputStream fis = null;
            ImageInputStream iis = null;
            try {
                fis = fis = new FileInputStream(images[i].getAbsoluteFile());
                BufferedInputStream bis = new BufferedInputStream(fis);
                Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(readImageFormat);
                ImageReader reader = it.next();
                //获取图片流
                iis = ImageIO.createImageInputStream(bis);
                reader.setInput(iis, true);
                ImageReadParam param = reader.getDefaultReadParam();
                //定义一个矩形
                Rectangle rect = new Rectangle(x, y, width, height);
                //提供一个 BufferedImage，将其用作解码像素数据的目标。
                param.setSourceRegion(rect);
                BufferedImage bi = reader.read(0, param);
                //保存新图片
                buffer.add(bi);
            }catch (IOException e){
                e.printStackTrace();
                Logger.getLogger("ImageIoUtils").log(Level.SEVERE,"裁剪失败");
            }finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("裁剪失败");
                    }
                }
                if (iis != null) {
                    try {
                        iis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return buffer;
    }

    /**
     * 转换图片格式
     *
     * @param srcPath
     * @param toPath
     * @param dstFormat
     * @return
     */
    public static boolean convertImageFormat(File srcPath, File toPath, String dstFormat) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 转换图片格式
     * @param srcPath
     * @param dstFormat
     * @return TYPE_INT_RGB格式图片
     */
    public static BufferedImage convertImageFormat(File srcPath, String dstFormat){

        BufferedImage bufferedImage;
        BufferedImage newBufferedImage=null;
        try {
            bufferedImage = ImageIO.read(srcPath);
            newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newBufferedImage;

    }

    public static void cvtColor(Mat srcmat, Mat destmat) {
        Imgproc.cvtColor(srcmat, destmat, Imgproc.COLOR_RGB2GRAY);
    }

    public static void threshold(Mat srcmat, Mat destmat) {
        //而二值化处理
        Imgproc.threshold(srcmat, destmat, 150, 255, Imgproc.THRESH_BINARY);
    }

    /**
     * 腐蚀增强
     * @param srcmat
     * @param fileextend 文件扩展名 .bmp
     */
    public static BufferedImage erodeToBufferImage(BufferedImage srcimg,String fileextend) {

        Mat srcmat=OpenCvUtils.BufImg2Mat(srcimg,BufferedImage.TYPE_INT_RGB, CvType.CV_8U);

        //定义腐蚀块的大小
        Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2, 2));

        Mat destmat=new Mat();

        Imgproc.erode(srcmat, destmat, element);
        BufferedImage img= OpenCvUtils.Mat2BufImg(destmat,fileextend);
        return img;
    }


    /** 内存中加载速度并未提高
     *         if (path.isDirectory()) {
     *             File[] files = path.listFiles();
     *             ArrayList<BufferedImage> buffer=ImageIoUtils.cropImageForList(files,115, 40, 380, 45, "bmp");
     *             Tesseract tesseract= new TesseractMul("chi_sim").getCurrTesseract();
     *             //根据环境选择数据流方式
     *             if (mode != null && mode.equals("console")) {
     *                 //Recognition(list, tesseract, files, "chi_sim");
     *                 for (int i = 0; i < buffer.size(); i++) {
     *                     try {
     *                         String s=tesseract.doOCR(buffer.get(i));
     *                         list.add(s);
     *                     } catch (TesseractException e) {
     *                         e.printStackTrace();
     *                     }
     *                 }
     *             } else {
     *                 Recognition(list, tesseract, files, "chi_sim");
     *             }
     *             try {
     *                 FileUtils.cleanDirectory(new File(discernShopImg.temppath));
     *             } catch (IOException e) {
     *                 e.printStackTrace();
     *             }
     *         }
     *         return list;
     */

}
