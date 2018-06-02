import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.junit.Test;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import utils.ImageIoUtils;
import utils.OpenCvUtils;
import utils.TesseractUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Arrays.asList;

public class TestImage {

    static{
        String path="E:\\2018中国软件杯\\opencv\\build\\java\\x64\\opencv_java341.dll";
        System.load(path);
    }

    private static final String srcPath="D:\\businessinformationdiscern_jar\\images";
    private static final String dstPath="Z:\\b\\";

    @Test//文字
    public void testCropImage2() throws IOException{

        File file = new File(srcPath);
        if (file.isDirectory()){
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                ImageIoUtils.cropImage(files[i].getAbsolutePath(),dstPath+files[i].getName(),120,40,380,44,"bmp","bmp");
            }
        }

    }

    @Test //数字
    public void testCropImage() throws IOException{

        File file = new File(srcPath);
        if (file.isDirectory()){
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                ImageIoUtils.cropImage(files[i].getAbsolutePath(),dstPath+files[i].getName(),150,0,300,47,"bmp","bmp");
            }
        }

    }
    public List<File> fileSort(List<File> files) {
        Collections.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.isDirectory() && o2.isFile())
                    return -1;
                if (o1.isFile() && o2.isDirectory())
                    return 1;
                System.out.println(o1.getName());
                return o1.getName().compareToIgnoreCase(o2.getName());

               // return  Integer.compare(Integer.valueOf(f1),Integer.valueOf(f2));
            }
        });
        return files;
    }
    @Test
    public void orderByName() {

        List<File> files = Arrays.asList(new File("Z:\\b").listFiles());
        fileSort(files);
        for (File f : files) {
            System.out.println(f.getName());
        }
    }
    @Test
    public void testClearWatermark() throws IOException{
        String srcPath1="Z:\\code";
        String dstPath1="Z:\\b\\";
        OpenCvUtils.fileToclearWatermark(srcPath1,dstPath1);
        String s1="E:\\2018中国软件杯\\Businessinfd\\out\\artifacts\\businessinformationdiscern_jar\\images";
        String s2="E:\\2018中国软件杯\\Businessinfd\\out\\artifacts\\businessinformationdiscern_jar\\srcdata\\";
       // OpenCvUtils.fileToclearWatermark(s1,s2);
    }

    @Test
    public void TestConvertImageFormat() throws IOException {
        String src="Z:\\天猫工商信息执照";
        String dst="Z:\\TEMP";//Z:\天猫工商信息执照

        File file = new File(src);
        if (file.isDirectory()){
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File dst1 = new File(dst + File.separator + i + ".bmp");
                ImageIoUtils.convertImageFormat(files[i],dst1,"bmp");
            }
        }

    }

    //图像的进一出处理测试
    @Test
    public void f2222() throws TesseractException {
        String src="Z:\\b";
        String des="Z:\\c\\";
        File file = new File(src);
        File[] files = file.listFiles();
        for (File file1 : files) {
            Mat mat = Imgcodecs.imread(file1.getAbsolutePath());
            Mat destmat = new Mat(mat.rows(), mat.cols(), CvType.CV_8UC1);
            //ImageIoUtils.cvtColor(mat,destmat);
            ImageIoUtils.threshold(mat,destmat);
            //ImageIoUtils.erode(mat,destmat);
            Imgcodecs.imwrite(des+file1.getName(),destmat);
        }
    }
    //测试进一出图片处理后的效果
    @Test
    public void testRecotation() throws TesseractException {
        String des="Z:\\c";
        File file = new File(des);
        Tesseract chi_sim = TesseractUtil.initCurrTesseract("chi_sim");
        if (file.isDirectory()){
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File file1 = new File(des + File.separator + i+".bmp");
                String ocr = chi_sim.doOCR(file1);
                System.out.println(ocr);
            }
        }

    }


    @Test
    public void f() throws TesseractException {
        String src="Z:\\code";
        //String dst="Z:\\TEMP";
        File file = new File(src);
        File[] files = file.listFiles();
       /* List<File> fileList = Arrays.asList(files);
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.isDirectory() && o2.isFile())
                    return -1;
                if (o1.isFile() && o2.isDirectory())
                    return 1;
                return o1.getName().compareTo(o2.getName());
            }
        });*/
        Tesseract ens = TesseractUtil.initCurrTesseract("eng");
        for (int i = 0; i <files.length ; i++) {
            //System.out.println("Z:\\TEMP\\"+i+".bmp");
            String s = ens.doOCR(new File("Z:\\code\\" + i + ".bmp"));
            System.out.println(s);
        }
    }
    @Test
    public void opencvTo(){
        String src="Z:\\1.png";
        String dest="Z:\\2.bmp";
        Mat mat = Imgcodecs.imread(src,Imgcodecs.IMREAD_COLOR);
        Imgcodecs.imwrite(dest,mat);

        BufferedImage bufferedImage;

        try {
            //read image file
            bufferedImage = ImageIO.read(new File(src));

            // create a blank, RGB, same width and height, and a white background
            BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);

            //TYPE_INT_RGB:创建一个RBG图像，24位深度，成功将32位图转化成24位

            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

            // write to jpeg file
            ImageIO.write(newBufferedImage, "bmp", new File(dest));

            System.out.println("Done");

        } catch (IOException e) {

            e.printStackTrace();

        }
    }
    @Test
    public void testtemp() throws TesseractException{
        String src="Z:\\1.jpg";
        File file = new File(src);
        Tesseract ens = TesseractUtil.initCurrTesseract("eng");
        String s = ens.doOCR(file);
        System.out.println(s);
    }
    //ITesseract instance = new Tesseract();
    //File tessDataFolder = LoadLibs.extractTessResources("tessdata"); // Maven build bundles English data
    //instance.setDatapath(tessDataFolder.getParent());
    //如果未将tessdata放在根目录下需要指定绝对路径
    //instance.setDatapath("E:/Businessinfd/businessinformationdiscern/tessdata");
    //instance.setLanguage("eng");
    // 这里对图片黑白处理,增强识别率.这里先通过截图,截取图片中需要识别的部分
    //BufferedImage textImage = ImageHelper.convertImageToGrayscale(ImageHelper.getSubImage(panel.image, startX, startY, endX, endY));
    // 图片锐化,自己使用中影响识别率的主要因素是针式打印机字迹不连贯,所以锐化反而降低识别率
    // textImage = ImageHelper.convertImageToBinary(textImage);
    // 图片放大5倍,增强识别率(很多图片本身无法识别,放大5倍时就可以轻易识,但是考滤到客户电脑配置低,针式打印机打印不连贯的问题,这里就放大5倍)
    //	textImage = ImageHelper.getScaledInstance(textImage, endX * 5, endY * 5);
    //BufferedImage grayImage = ImageHelper.convertImageToBinary(ImageIO.read(imageFile));
    //ImageIO.write(grayImage, "jpg", new File("data/", "test2.jpg"));
    @Test
    public void ffffffffff(){
        System.out.println( System.getProperty("user.dir"));
            String sre="9.png";
        String substring = sre.substring(sre.lastIndexOf("."), sre.length());
        System.out.println(substring);
    }
}
