import utils.OCR;

import java.io.File;
import java.io.IOException;

public class TestOcr {


    public static void main(String[] args) {
        //输入图片地址
        String path = "E://cxf//develop-workspace-new//testImageRecognition//src//2.jpg";
        File root = new File(System.getProperty("user.dir") + "/images/1.png");
        try {
            String valCode = new OCR().recognizeText(root ,"png");
            System.out.println(valCode);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
