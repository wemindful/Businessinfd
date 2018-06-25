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
import java.util.Collections;
import java.util.Comparator;

/**
 * @program: businessinformationdiscern
 * @description: 测试主流程
 * @author: Mr.Dai
 * @create: 2018-06-25 15:01
 **/
public class TestMain {

    /*private final static String basepath = System.getProperty("user.dir");//基础目录
    private final static String temppath = System.getProperty("user.dir") + "/temp/";//识别片段缓存
    private final static String datapath = System.getProperty("user.dir") + "/data";//储存去水印后照片
    private final static String excel = System.getProperty("user.dir") + "/工商信息执照.xls";*/

    private final static String basepath ="Z:\\textrecotation";
    private final static String temppath = "Z:\\textrecotation\\temp\\";
    private final static String datapath = "Z:\\textrecotation\\data";
    private final static String excel ="Z:\\textrecotation\\工商信息执照.xls";

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
        File path = new File(TestMain.datapath);
        if (path.isDirectory()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                try {
                    ImageIoUtils.cropImage(files[i].getAbsolutePath(),temppath+files[i].getName(),115,40,380,45,"bmp","bmp");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("裁剪失败");
                }
            }
        }
        Tesseract tesseract = TesseractUtil.initCurrTesseract("chi_sim");
        File[] files = new File(temppath).listFiles();
        if (files==null)return new ArrayList<>();
        Recognition(list, tesseract, files,"chi_sim");
        try {
            FileUtils.cleanDirectory(new File(TestMain.temppath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;

    }

    /**
     *
     * @param list
     * @param tesseract
     * @param files
     * @param Langflag 标识语言类型
     */
    private static void Recognition(ArrayList<String> list, Tesseract tesseract, File[] files,String Langflag) {

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

        if(Langflag.equals("eng")){
            for (File file1 : filelist) {
                String cname = null;
                try {
                    cname = tesseract.doOCR(file1);
                } catch (TesseractException e) {
                    e.printStackTrace();
                    System.out.println("识别失败");
                }
                //因为 Tesseract  识别 ）出现失误 所以进行手动矫正
                if(cname!=null&&cname.contains(")")){
                    cname=cname.replace(")","J");
                }
                String fileName = file1.toString().substring(file1.toString().lastIndexOf("\\") + 1);
                System.out.println("图片名：" + fileName + " 识别结果：" + cname);
                list.add(cname);
            }
        }else {
            for (File file : filelist) {
                String cname = null;
                try {
                    cname = tesseract.doOCR(file);
                } catch (TesseractException e) {
                    e.printStackTrace();
                    System.out.println("识别失败");
                }
                //因为 出现“：” 为了不降低正确率 那么手动处理：
                if(cname!=null&&cname.contains(":")||cname.contains("ˇ")||cname.contains(".")){
                    cname=cname.replace(":","");
                    cname=cname.replace("ˇ","");
                    cname=cname.replace(".","");
                }
                String fileName = file.toString().substring(file.toString().lastIndexOf("\\") + 1);
                System.out.println("图片名：" + fileName + " 识别结果：" + cname);
                list.add(cname);
            }
        }
    }

    /**
     * 按照指定办法读取文件
     * @param filename
     * @return
     */
    private static Integer fequals(String filename){
        int x = filename.indexOf(".");
        String string2 = filename.substring(0,x);
        char[] cs = string2.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < cs.length; i++) {
            if(Character.isDigit(cs[i])) {
                builder.append(cs[i]);
            }
        }
        return Integer.parseInt(builder.toString());

    }


    /**
     * //剪切出企业编号,保存.并且清空缓存
     * @return 企业编号
     * @throws IOException
     * @throws TesseractException
     */
    private static ArrayList<String> GetCompanyId()  {
        ArrayList<String> list = new ArrayList<>();
        File path = new File(TestMain.datapath);
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
        Recognition(list, tesseract, files,"eng");
        try {
            FileUtils.cleanDirectory(new File(TestMain.temppath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 初始化目录环境 //判断缓存目录是否为null 如果是就清空
     */
    private static void initEnvironment() throws IOException {
        File root = new File(TestMain.basepath + "/天猫工商信息执照");
        File temp = new File(TestMain.basepath + "/temp");
        File xlsx = new File(TestMain.basepath + "/工商信息执照.xlsx");
        if (!root.exists()||!temp.exists()||xlsx.exists()){
            root.mkdir();
            temp.mkdir();
            xlsx.delete();
        }
        // 转换为24rgb 格式
        FileUtils.cleanDirectory(new File(TestMain.basepath + "/data"));//data 转变格式后图片
        FileUtils.cleanDirectory(new File(TestMain.basepath + "/images"));//去除水印后图片
        if (root.isDirectory()){
            File[] files = root.listFiles();
            for (int i = 0; i < files.length; i++) {//Z:\天猫工商信息执照
                File dstImg = new File(TestMain.basepath + "/images"+ File.separator + i + ".bmp");
                ImageIoUtils.convertImageFormat(files[i],dstImg,"bmp");
            }
        }
        //去除水印
        OpenCvUtils.fileToclearWatermark(TestMain.basepath + "/images",TestMain.basepath + "\\data\\");
        FileUtils.cleanDirectory(temp);
        FileUtils.cleanDirectory(new File(TestMain.basepath + "/images"));
    }

}
