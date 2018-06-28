package services;

import com.xuxueli.poi.excel.ExcelExportUtil;
import domain.ShopNetDTO;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.io.FileUtils;
import utils.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: businessinformationdiscern
 * @description: 识别图片信息的包装类
 * @author: Mr.Dai
 * @create: 2018-06-25 16:23
 **/
public class discernShopImg {

    private static String basepath = System.getProperty("user.dir");//基础目录
    private static String temppath = basepath + "/temp/";//识别工商名字缓存
    private static String tempcpath = basepath + "/tempc/";//识别工商id缓存
    private static String datapath = basepath + "/data";//储存去水印后照片
    private static String excel = basepath + "/工商信息执照.xls";
    private static String mode = "console";//定义两种运行的模式 分别控制台 gui

    //console缓存数据
    public ArrayList<ShopNetDTO> objects;
    //gui 缓存数据
    private static ArrayList<String> companyNamelist = new ArrayList<>();
    private static ArrayList<String> companyIdlist = new ArrayList<>();

    //gui 进度条值
    public static Integer ImageNumber=null;


    public discernShopImg(String basepath, String mode) {
        this.basepath = basepath;
        this.temppath = basepath + "/temp/";
        this.datapath = basepath + "/data";
        this.tempcpath= basepath + "/tempc/";
        this.excel = basepath + "/工商信息执照.xls";
        this.mode = mode;
    }

    public discernShopImg() {
    }

    /**
     * 开始识别的唯一包装方法
     *
     * @return 是否处理正常
     */
    public boolean StartDiscern() {
        long startTime = System.currentTimeMillis();
        try {
            initEnvironment();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        ExecutorService exe= Executors.newFixedThreadPool(2);
        exe.execute(() -> {
            ArrayList<String> companyId = GetCompanyId();
            companyIdlist=companyId;
        });
        exe.execute(() -> {
            ArrayList<String> companyName = GetCompanyName();
            companyNamelist=companyName;
        });
        exe.shutdown();

        while (true){
            if(exe.isTerminated()){
                objects = new ArrayList<>();
                //数据清洗,导出excel
                for (int i = 0; i < companyIdlist.size(); i++) {
                    ShopNetDTO dto = new ShopNetDTO(companyNamelist.get(i), companyIdlist.get(i));
                    objects.add(dto);
                }
                ExcelExportUtil.exportToFile(excel, objects);
                try {
                    Desktop.getDesktop().open(new File(basepath));
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("无法打开文件目录");
                }
                long endTime = System.currentTimeMillis();
                System.out.println("处理时间:" + (endTime - startTime) / 1000);
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            FileUtils.cleanDirectory(new File(discernShopImg.basepath + "/images"));//去除水印后图片
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 剪切出企业名称,保存.并且清空缓存
     *
     * @return企业名称
     */
    private static ArrayList<String> GetCompanyName() {
        ArrayList<String> list = new ArrayList<>();
        File path = new File(discernShopImg.datapath);
        if (path.isDirectory()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                try {
                    ImageIoUtils.cropImage(files[i].getAbsolutePath(), temppath + files[i].getName(), 115, 40, 380, 45, "bmp", "bmp");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("裁剪失败");
                }
            }
        }
        //测试
        //Tesseract tesseract = TesseractUtil.initCurrTesseract("chi_sim");
        Tesseract tesseract= new TesseractMul("chi_sim").getCurrTesseract();
        File[] files = new File(temppath).listFiles();
        if (files == null) return new ArrayList<>();
        //根据环境选择数据流方式
        if (mode != null && mode.equals("console")) {
            Recognition(list, tesseract, files, "chi_sim");
        } else {
            Recognition(list, tesseract, files, "chi_sim");
        }
        try {
            FileUtils.cleanDirectory(new File(discernShopImg.temppath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;

    }

    /**
     * @param list
     * @param tesseract
     * @param files
     * @param Langflag  标识语言类型
     */
    private static void Recognition(ArrayList<String> list, Tesseract tesseract, File[] files, String Langflag) {

        ArrayList<File> filelist = new ArrayList<>();
        for (File file : files) {
            filelist.add(file);
        }
        Collections.sort(filelist, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.isDirectory() && o2.isFile())
                    return -1;
                if (o1.isFile() && o2.isDirectory())
                    return 1;
                Integer f = fequals(o1.getName());
                Integer f2 = fequals(o2.getName());
                return Integer.compare(f, f2);
            }
        });

        if (Langflag.equals("eng")) {
            for (File file1 : filelist) {
                String cname = null;
                try {
                    cname = tesseract.doOCR(file1);
                    ImageNumber--;
                } catch (TesseractException e) {
                    e.printStackTrace();
                    System.out.println("识别失败");
                }
                //因为 Tesseract  识别 ）出现失误 所以进行手动矫正
                if (cname != null && cname.contains(")")) {
                    cname = cname.replace(")", "J");
                }
                String fileName = file1.toString().substring(file1.toString().lastIndexOf("\\") + 1);
                System.out.println("图片名：" + fileName + " 识别结果：" + cname);
                list.add(cname);
            }
        } else {
            for (File file : filelist) {
                String cname = null;
                try {
                    ImageNumber--;
                    cname = tesseract.doOCR(file);
                } catch (TesseractException e) {
                    e.printStackTrace();
                    System.out.println("识别失败");
                }
                //因为 出现“：” 为了不降低正确率 那么手动处理：
                if (cname != null && cname.contains(":") || cname.contains("ˇ") || cname.contains(".")) {
                    cname = cname.replace(":", "");
                    cname = cname.replace("ˇ", "");
                    cname = cname.replace(".", "");
                }
                String fileName = file.toString().substring(file.toString().lastIndexOf("\\") + 1);
                System.out.println("图片名：" + fileName + " 识别结果：" + cname);
                list.add(cname);
            }
        }
    }

    /**
     * 按照指定办法读取文件
     *
     * @param filename
     * @return
     */
    private static Integer fequals(String filename) {
        int x = filename.indexOf(".");
        String string2 = filename.substring(0, x);
        char[] cs = string2.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < cs.length; i++) {
            if (Character.isDigit(cs[i])) {
                builder.append(cs[i]);
            }
        }
        return Integer.parseInt(builder.toString());

    }


    /**
     * //剪切出企业编号,保存.并且清空缓存
     *
     * @return 企业编号
     * @throws IOException
     * @throws TesseractException
     */
    private static ArrayList<String> GetCompanyId() {
        ArrayList<String> list = new ArrayList<>();
        File path = new File(discernShopImg.datapath);
        if (path.isDirectory()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                try {
                    ImageIoUtils.cropImage(files[i].getAbsolutePath(), tempcpath + files[i].getName(), 150, 0, 300, 50, "bmp", "bmp");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("裁剪失败");
                }
            }
        }
        //Tesseract tesseract = TesseractUtil.initCurrTesseract("eng");
        Tesseract tesseract= new TesseractMul("eng").getCurrTesseract();
        File[] files = new File(tempcpath).listFiles();
        //根据环境选择数据流方式
        if (mode != null && mode.equals("console")) {
            Recognition(list, tesseract, files, "eng");
        } else {
            Recognition(list, tesseract, files, "eng");
        }
        try {
            FileUtils.cleanDirectory(new File(discernShopImg.tempcpath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 初始化目录环境 //判断缓存目录是否为null 如果是就清空
     */
    public static void initEnvironment() throws IOException {
        File root = new File(discernShopImg.basepath + "/天猫工商信息执照");
        File temp = new File(discernShopImg.basepath + "/temp");
        File tempc = new File(discernShopImg.basepath + "/tempc");
        File xlsx = new File(discernShopImg.basepath + "/工商信息执照.xlsx");
        if (!root.exists() || !temp.exists() || xlsx.exists()) {
            root.mkdir();
            temp.mkdir();
            tempc.mkdir();
            xlsx.delete();
        }
         FileSuffixUtils.clearIllegalFile(basepath);
        //FileSuffixUtils.clearIllegalImage(basepath);
        // 转换为24rgb 格式
        FileUtils.cleanDirectory(new File(discernShopImg.basepath + "/data"));//data 转变格式后图片
        FileUtils.cleanDirectory(new File(discernShopImg.basepath + "/images"));//去除水印后图片
        if (root.isDirectory()) {
            File[] files = root.listFiles();
            ImageNumber=files.length*2;//取得图片个数
            for (int i = 0; i < files.length; i++) {//Z:\天猫工商信息执照
                File dstImg = new File(discernShopImg.basepath + "/images" + File.separator + i + ".bmp");
                ImageIoUtils.convertImageFormat(files[i], dstImg, "bmp");
            }
        }
        //去除水印
        OpenCvUtils.fileToclearWatermark(discernShopImg.basepath + "/images", discernShopImg.basepath + "\\data\\");
        FileUtils.cleanDirectory(temp);
        FileUtils.cleanDirectory(new File(discernShopImg.basepath + "/images"));
    }

    public static ArrayList<String> getCompanyNamelist() {
        return companyNamelist;
    }

    public static ArrayList<String> getCompanyIdlist() {
        return companyIdlist;
    }

    public static Integer getImageNumber() {
        return ImageNumber;
    }

    public ArrayList<ShopNetDTO> getObjects() {
        return objects;
    }
}
