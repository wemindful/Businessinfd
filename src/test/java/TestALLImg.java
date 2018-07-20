import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.ImageHelper;
import net.sourceforge.tess4j.util.ImageIOHelper;
import org.junit.Test;
import services.DiscernALLImg;
import services.ImagesMegToText;
import sun.net.www.content.image.png;
import utils.TesseractMul;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @program: businessinformationdiscern
 * @description: 、
 * @author: Mr.Dai
 * @create: 2018-07-19 11:27
 **/
public class TestALLImg {

    @Test
    public void test1() throws IOException, TesseractException {
        long stime=System.currentTimeMillis();
        BufferedImage img=ImageIO.read(new FileInputStream(new File("Z:\\test1\\2.bmp")));
        BufferedImage binaryimage=ImageHelper.convertImageToBinary(img);
        Tesseract tesseract= new TesseractMul("chi_sim").getCurrTesseract();
        String str=tesseract.doOCR(binaryimage);
        System.out.println(str);
        long etime=System.currentTimeMillis();
        System.out.println(((etime-stime)/3600));
    }

    @Test
    public void testmergeTiff() throws IOException {
        List<BufferedImage> bufferimg=ImageIOHelper.getImageList(new File("Z:\\src"));
    }

    /**
     * 异常图片：w 810 h 579
     * 正常图片：w 1200 h 710
     *
     * @throws IOException
     */
    @Test
    public void testGetImgWidOrHei() throws IOException{
        File path=new File("Z:\\src");
        for (File file : path.listFiles()) {
            BufferedImage img=ImageIO.read(file);
            System.out.println(img.getWidth());
            System.out.println(img.getHeight());
            System.out.println("---------------------");
        }
    }

    /**
     * 当宽不等于1200 直接删除
     */
    @Test
    public void testNotEagleImg() throws IOException{
        File path=new File("Z:\\src");
        for (File file : path.listFiles()) {
            BufferedImage img=ImageIO.read(file);
            if(img.getWidth()!=1200){
                file.delete();
            }
        }
    }

    @Test
    public void testsplitBufferImgesMap(){
        //new ImagesMegToText().splitBufferImgesMap();
    }

    /**
     * 识别map中bufferimages代码
     */
    @Test
    public void testsplit() throws TesseractException {
        Map<Integer,BufferedImage> map= new DiscernALLImg().imgFixedProcess();
        for (int i = 0; i < map.size(); i++) {
            Tesseract tesseract= new TesseractMul("chi_sim").getCurrTesseract();
            String str=tesseract.doOCR(map.get(i));
            System.out.println(str);
            System.out.println("----------------------------");
        }
    }
    @Test
    public void testImagesMegToText(){
        new ImagesMegToText().StartDiscern();
    }

    @Test
    public void testVCode() throws TesseractException, IOException {
        long stime=System.currentTimeMillis();
        BufferedImage img=ImageIO.read(new FileInputStream(new File("Z:\\yanzhengma\\3.jpg")));
        BufferedImage binaryimage=ImageHelper.convertImageToBinary(img);
        Tesseract tesseract= new TesseractMul("eng").getCurrTesseract();
        String str=tesseract.doOCR(binaryimage);
        System.out.println(str);
        long etime=System.currentTimeMillis();
        System.out.println(((etime-stime)/1000));
    }

}
