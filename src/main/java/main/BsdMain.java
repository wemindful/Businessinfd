package main;

import com.xuxueli.poi.excel.ExcelExportUtil;
import domain.ShopNetDTO;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.io.FileUtils;
import utils.ImageIoUtils;
import utils.OpenCvUtils;
import utils.TesseractUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BsdMain {

    private final static String basepath = System.getProperty("user.dir");//基础目录
    private final static String temppath = System.getProperty("user.dir") + "/temp/";//识别片段缓存
    private final static String datapath = System.getProperty("user.dir") + "/data";//储存去水印后照片
    private final static String excel = System.getProperty("user.dir") + "/工商信息执照.xls";
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        initEnvironment();

        ArrayList<String> companyId = GetCompanyId();
        ArrayList<String> companyName = GetCompanyName();
        ArrayList<ShopNetDTO> objects = new ArrayList<>();
        //数据清洗,导出excel
        for (int i = 0; i < companyId.size(); i++) {
            ShopNetDTO dto = new ShopNetDTO(companyName.get(i), companyId.get(i));
            objects.add(dto);
        }
        ExcelExportUtil.exportToFile(excel,objects);
        long endTime = System.currentTimeMillis();
        System.out.println("处理时间:"+(endTime-startTime)/1000);
    }

    /**
     * 剪切出企业名称,保存.并且清空缓存
     * @return企业名称
     */
    private static ArrayList<String> GetCompanyName() {
        ArrayList<String> list = new ArrayList<>();
        File path = new File(BsdMain.datapath);
        if (path.isDirectory()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                try {
                    ImageIoUtils.cropImage(files[i].getAbsolutePath(),temppath+files[i].getName(),110,40,380,45,"bmp","bmp");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("裁剪失败");
                }
            }
        }
        Tesseract tesseract = TesseractUtil.initCurrTesseract("chi_sim");
        File[] files = new File(temppath).listFiles();
        Recognition(list, tesseract, files);
        try {
            FileUtils.cleanDirectory(new File(BsdMain.temppath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;

    }

    private static void Recognition(ArrayList<String> list, Tesseract tesseract, File[] files) {
        for (File file1 : files) {
            String cname = null;
            try {
                cname = tesseract.doOCR(file1);
            } catch (TesseractException e) {
                e.printStackTrace();
                System.out.println("识别失败");
            }
            String fileName = file1.toString().substring(file1.toString().lastIndexOf("\\")+1);
            System.out.println("图片名：" + fileName +" 识别结果："+cname);
            list.add(cname);
        }
    }

    /**
     * //剪切出企业编号,保存.并且清空缓存
     * @return 企业编号
     * @throws IOException
     * @throws TesseractException
     */
    private static ArrayList<String> GetCompanyId()  {
        ArrayList<String> list = new ArrayList<>();
        File path = new File(BsdMain.datapath);
        if (path.isDirectory()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                try {
                    ImageIoUtils.cropImage(files[i].getAbsolutePath(), temppath + files[i].getName(), 150,0,300,50, "bmp", "bmp");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("裁剪失败");
                }
            }
        }
        Tesseract tesseract = TesseractUtil.initCurrTesseract("eng");
        File[] files = new File(temppath).listFiles();
        Recognition(list, tesseract, files);
        try {
            FileUtils.cleanDirectory(new File(BsdMain.temppath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 初始化目录环境 //判断缓存目录是否为null 如果是就清空
     */
    private static void initEnvironment() throws IOException{
        File root = new File(BsdMain.basepath + "/天猫工商信息执照");
        File temp = new File(BsdMain.basepath + "/temp");
        File xlsx = new File(BsdMain.basepath + "/工商信息执照.xlsx");
        if (!root.exists()||!temp.exists()||xlsx.exists()){
            root.mkdir();
            temp.mkdir();
            xlsx.delete();
        }
        // 转换为24rgb 格式
        FileUtils.cleanDirectory(new File(BsdMain.basepath + "/data"));//data 转变格式后图片
        FileUtils.cleanDirectory(new File(BsdMain.basepath + "/images"));//去除水印后图片
        if (root.isDirectory()){
            File[] files = root.listFiles();
            for (int i = 0; i < files.length; i++) {//Z:\天猫工商信息执照
                File dstImg = new File(BsdMain.basepath + "/images"+ File.separator + i + ".bmp");
                ImageIoUtils.convertImageFormat(files[i],dstImg,"bmp");
            }
        }
        //去除水印
        OpenCvUtils.fileToclearWatermark(BsdMain.basepath + "/images",BsdMain.basepath + "\\data\\");
        FileUtils.cleanDirectory(temp);
    }
}
