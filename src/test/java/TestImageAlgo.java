import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.junit.Test;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import utils.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @program: businessinformationdiscern
 * @description: 测试图片算法
 * @author: Mr.Dai
 * @create: 2018-07-26 23:26
 **/
public class TestImageAlgo {
    static {
        System.load(SystemSetting.loadLib());
    }

    /*
    腐蚀增强
     */
    @Test
    public void test1(){

        //Mat srcmat=Imgcodecs.imread("z:/0.bmp");
        try {
        BufferedImage srcimg=ImageIO.read(new File("z:/0.bmp"));

        Mat srcmat=OpenCvUtils.BufImg2Mat(srcimg,BufferedImage.TYPE_INT_RGB, CvType.CV_8U);


        Mat destmat=new Mat();
        Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2, 2));
        Imgproc.erode(srcmat, destmat, element);
        //Imgcodecs.imwrite("z:/2.bmp",destmat);

        BufferedImage img= OpenCvUtils.Mat2BufImg(destmat,".bmp");
        ImageIO.write(img,"bmp",new FileOutputStream("z:/22.bmp"));
        Tesseract tesseract=new TesseractMul("chi_sim").getCurrTesseract();

            String ocr=tesseract.doOCR(img);
            System.out.println(ocr);
        } catch (TesseractException | IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 去除水印
     */
    @Test
    public void test2(){
        String srcPath1="Z:\\转换格式后";
        File file=new File(srcPath1);
        File[] files=file.listFiles();
        for (int i = 0; i < files.length; i++) {
            try {
               BufferedImage img= ImageIO.read(files[i]);
               BufferedImage clearimg= ClearWaterImgesInit.fileToclearWatermark(img);
               ImageIO.write(clearimg,"bmp",new FileOutputStream(files[i]));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 8邻域降噪,又有点像9宫格降噪;即如果9宫格中心被异色包围，则同化
     * @param pNum 默认值为1
     */
    @Test
    public void test4() {
        navieRemoveNoise(10);
        //contoursRemoveNoise(20);
    }

    public static  void navieRemoveNoise(int pNum) {

      /*  Mat mat=new Mat();
        Imgproc.cvtColor(Imgcodecs.imread("z:/zaodain.bmp"), mat, Imgproc.COLOR_RGB2GRAY);*/

        File imgFile = new File("z:/zaodian.png");
        Mat src = Imgcodecs.imread(imgFile.toString(), Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);

        ImageUtils img= new ImageUtils(src);



        int i, j, m, n, nValue, nCount;
        int nWidth = img.getWidth(), nHeight = img.getHeight();


        // 对图像的边缘进行预处理
        for (i = 0; i < nWidth; ++i) {
            img.setPixel(i, 0, ImageUtils.WHITE);
            img.setPixel(i, nHeight - 1, ImageUtils.WHITE);
        }

        for (i = 0; i < nHeight; ++i) {
            img.setPixel(0, i, ImageUtils.WHITE);
            img.setPixel(nWidth - 1, i, ImageUtils.WHITE);
        }

        // 如果一个点的周围都是白色的，而它确是黑色的，删除它
        for (j = 1; j < nHeight - 1; ++j) {
            for (i = 1; i < nWidth - 1; ++i) {
                nValue = img.getPixel(j, i);
                if (nValue == 0) {
                    nCount = 0;
                    // 比较以(j ,i)为中心的9宫格，如果周围都是白色的，同化
                    for (m = j - 1; m <= j + 1; ++m) {
                        for (n = i - 1; n <= i + 1; ++n) {
                            if (img.getPixel(m, n) == 0) {
                                nCount++;
                            }
                        }
                    }
                    if (nCount <= pNum) {
                        // 周围黑色点的个数小于阀值pNum,把该点设置白色
                        img.setPixel(j, i, ImageUtils.WHITE);
                    }
                } else {
                    nCount = 0;
                    // 比较以(j ,i)为中心的9宫格，如果周围都是黑色的，同化
                    for (m = j - 1; m <= j + 1; ++m) {
                        for (n = i - 1; n <= i + 1; ++n) {
                            if (img.getPixel(m, n) == 0) {
                                nCount++;
                            }
                        }
                    }
                    if (nCount >= 7) {
                        // 周围黑色点的个数大于等于7,把该点设置黑色;即周围都是黑色
                        img.setPixel(j, i, ImageUtils.BLACK);
                    }
                }
            }
        }
        img.saveImg("z:/ok.png");
    }
    /**
     * 连通域降噪
     * @param pArea 默认值为1
     */
    public void contoursRemoveNoise(double pArea) {

        File imgFile = new File("z:/zaodian.png");
        Mat src = Imgcodecs.imread(imgFile.toString(), Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
        ImageUtils img= new ImageUtils(src);

        int i, j, color = 1;
        int nWidth = img.getWidth(), nHeight =  img.getHeight();

        for (i = 0; i < nWidth; ++i) {
            for (j = 0; j < nHeight; ++j) {
                if ( img.getPixel(j, i) == ImageUtils.BLACK) {
                    //用不同颜色填充连接区域中的每个黑色点
                    //floodFill就是把一个点x的所有相邻的点都涂上x点的颜色，一直填充下去，直到这个区域内所有的点都被填充完为止
                    Imgproc.floodFill(img.getMat(), new Mat(), new Point(i, j), new Scalar(color));
                    color++;
                }
            }
        }

        //统计不同颜色点的个数
        int[] ColorCount = new int[255];

        for (i = 0; i < nWidth; ++i) {
            for (j = 0; j < nHeight; ++j) {
                if ( img.getPixel(j, i) != 255) {
                    ColorCount[ img.getPixel(j, i) - 1]++;
                }
            }
        }

        //去除噪点
        for (i = 0; i < nWidth; ++i) {
            for (j = 0; j < nHeight; ++j) {

                if (ColorCount[ img.getPixel(j, i) - 1] <= pArea) {
                    img.setPixel(j, i, ImageUtils.WHITE);
                }
            }
        }

        for (i = 0; i < nWidth; ++i) {
            for (j = 0; j < nHeight; ++j) {
                if ( img.getPixel(j, i) < ImageUtils.WHITE) {
                    img.setPixel(j, i, ImageUtils.BLACK);
                }
            }
        }
        img.saveImg("z:/ok.png");
    }

}
