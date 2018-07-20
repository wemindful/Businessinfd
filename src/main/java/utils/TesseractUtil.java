package utils;

import main.AllConfig;
import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.Tesseract;
/**
 * @Description:  单例工具类
 * @Param:
 * @return:
 * @Author: Dai.GuoWei
 * @Date: 2018/6/5
 */
public class TesseractUtil {

    private volatile static Tesseract singletonTesseract;

   private TesseractUtil(){}

    private static Tesseract getInstance() {
        if (singletonTesseract == null) {
            synchronized (TesseractUtil.class) {
                if (singletonTesseract == null) {
                    singletonTesseract = new Tesseract();
                }
            }
        }
        return singletonTesseract;
    }

    /**
     * 取得适合当前的tesseract
     * @param languageName 语言名字
     * @return Tesseract
     */
    public static Tesseract initCurrTesseract(String languageName){
        Tesseract instance = getInstance();
        instance.setLanguage(languageName);
        instance.setDatapath(AllConfig.tessdata);
        return instance;
    }


}
