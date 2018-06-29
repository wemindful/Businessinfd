package utils;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;
import java.nio.file.FileSystemException;

public class OpenCvUtils {

    static{
        //System.load(Core.NATIVE_LIBRARY_NAME);
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String path=System.getProperty("user.dir")+ "\\opencv\\x64\\opencv_java341.dll";
        System.load(path);
    }

    /**
     *
     * @param srcMat 源水印图像
     * @return 去除水印后的图像
     */
    private static Mat clearWatermark( Mat srcMat){
        Mat destmat = new Mat(srcMat.height(),srcMat.width(),CvType.CV_8UC1);
        for (int y = 0; y < srcMat.height(); y++) {
            for (int x = 0; x < srcMat.width(); x++) {
                double[] data = srcMat.get(y, x);
                for (int i = 0; i < data.length; i++) {
                    if (data[i]==229){//水印色
                        data[i]=255;//白色
                    }
                }
                destmat.put(y,x,data);
            }
        }
          return destmat;
    }

    public static void fileToclearWatermark(String srcPath,String dstPath){
        File root = new File(srcPath);
        if (root.isFile()){
            try {
                throw new FileSystemException("必须为一个目录");
            } catch (FileSystemException e) {
                e.printStackTrace();
            }
        }else {
            File[] files = root.listFiles();//"Z:\\code\\";
            for (File file : files) {
                  Mat mat = Imgcodecs.imread(file.getPath());
                  Mat mat1 = clearWatermark(mat);
                  Imgcodecs.imwrite(dstPath+file.getName(),mat1);
                  System.out.println(dstPath+file.getName());
            }
            //文件目录不能带有中文
           /* for (int i = 0; i < files.length; i++) {//Z:\TEMP\
                System.out.println(dstPath+i+".bmp");
                Mat mat = Imgcodecs.imread(dstPath+i+".bmp");
                Mat mat1 = clearWatermark(mat);
                //System.out.println(dstPath+i+".bmp");
               // Imgcodecs.imwrite(dstPath+i+".bmp",mat1);
                System.out.println(Imgcodecs.imwrite(dstPath+i+".bmp",mat1));
            }*/
        }


    }

}
