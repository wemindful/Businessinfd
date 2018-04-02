

import java.awt.image.BufferedImage;
import java.io.File;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.ImageHelper;
import net.sourceforge.tess4j.util.LoadLibs;

import javax.imageio.ImageIO;

/**
 * 验证码识别（图片名即为验证码数字）
 * @author drguo
 *
 */
public class VCR {
	public static void main(String[] args) throws Exception {
		File root = new File(System.getProperty("user.dir") + "/images");
		ITesseract instance = new Tesseract();
		//File tessDataFolder = LoadLibs.extractTessResources("tessdata"); // Maven build bundles English data
		//instance.setDatapath(tessDataFolder.getParent());
		//如果未将tessdata放在根目录下需要指定绝对路径
		//instance.setDatapath("E:/2018中国软件杯/businessinformationdiscern/tessdata");
		instance.setLanguage("eng");
		// 这里对图片黑白处理,增强识别率.这里先通过截图,截取图片中需要识别的部分
//BufferedImage textImage = ImageHelper.convertImageToGrayscale(ImageHelper.getSubImage(panel.image, startX, startY, endX, endY));
// 图片锐化,自己使用中影响识别率的主要因素是针式打印机字迹不连贯,所以锐化反而降低识别率
// textImage = ImageHelper.convertImageToBinary(textImage);
// 图片放大5倍,增强识别率(很多图片本身无法识别,放大5倍时就可以轻易识,但是考滤到客户电脑配置低,针式打印机打印不连贯的问题,这里就放大5倍)
	//	textImage = ImageHelper.getScaledInstance(textImage, endX * 5, endY * 5);
		//BufferedImage grayImage = ImageHelper.convertImageToBinary(ImageIO.read(imageFile));
		//ImageIO.write(grayImage, "jpg", new File("data/", "test2.jpg"));
		try {
			File[] files = root.listFiles();
			for (File file : files) {
				String result = instance.doOCR(file);
				String fileName = file.toString().substring(file.toString().lastIndexOf("\\")+1);
				System.out.println("图片名：" + fileName +" 识别结果："+result);
			}
		} catch (TesseractException e) {
			System.err.println(e.getMessage());
		}
    }
}