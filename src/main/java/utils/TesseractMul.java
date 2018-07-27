package utils;

import main.AllConfig;
import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.Tesseract;
import sun.security.krb5.Config;

import java.io.File;

/**
 * @program: businessinformationdiscern
 * @description: Tesseract多例工具类
 * @author: Mr.Dai
 * @create: 2018-06-28 17:12
 **/
public class TesseractMul {

     private final static String path = AllConfig.tessdata; //+File.separator+"tessdata";//基础目录
    //测试目录
    // private final static String path = "D:\\businessdiscern\\businessinformationdiscern_jar\\tessdata";//基础目录

    private String Language="";
    public TesseractMul(String lang){
        this.Language=lang;
    }

    private TesseractMul(){

    }

    public Tesseract getCurrTesseract(){
        Tesseract tesseract=new Tesseract();
        tesseract.setLanguage(Language);
        tesseract.setDatapath(path);
        tesseract.setOcrEngineMode(ITessAPI.TessOcrEngineMode.OEM_LSTM_ONLY);
        return tesseract;
    }

}
