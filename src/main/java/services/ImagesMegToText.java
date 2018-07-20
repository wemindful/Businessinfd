package services;

import com.google.common.collect.Lists;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import utils.TesseractMul;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @program: businessinformationdiscern
 * @description: 数据识别
 * @author: Mr.Dai
 * @create: 2018-07-19 15:29
 **/
public class ImagesMegToText {

    //缓存工商原始数据
    private  ArrayList<String> oneCompanyData = new ArrayList<>();
    //private CopyOnWriteArrayList<String> oneCompanyData=new CopyOnWriteArrayList();


    /**
     * 识别并且返回数据
     * @param list
     * @param tesseract
     * @param listBufferImg
     */

    private void Recognition(List<String> list, Tesseract tesseract, List<BufferedImage> listBufferImg){
        for (int i = 0; i < listBufferImg.size(); i++) {
            try {
                String MessgaeData=tesseract.doOCR(listBufferImg.get(i));
                System.out.println(MessgaeData);
                //System.out.println("-------------------");
                list.add(MessgaeData);
            } catch (TesseractException e) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,"识别失败");
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据list开出多个线程，并行化加快识别速度
     * @return 运行正确
     */
    public boolean StartDiscern() {
        long startTime = System.currentTimeMillis();
        List<List<BufferedImage>> lists=splitBufferImgesMap();
        ExecutorService exe= Executors.newFixedThreadPool(lists.size());
        for (int i = 0; i < lists.size(); i++) {
            final int x=i;
            exe.execute(()->{
                List<String> list=Collections.synchronizedList(oneCompanyData);
                Recognition(list,new TesseractMul("chi_sim").getCurrTesseract(),lists.get(x));
            });
        }
        exe.shutdown();
        ArrayList<String> objects;
        while (true){
            objects = new ArrayList<>();
            if(exe.isTerminated()){
                for (int i = 0; i < oneCompanyData.size(); i++) {
                    objects.add(oneCompanyData.get(i));
                }
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(objects.size());
        long endTime = System.currentTimeMillis();
        System.out.println("处理时间:" + (endTime - startTime) / 1000);
        return  true;
    }
    /**
     *
     * @return 分割为多个bufferimages List
     */
    private List<List<BufferedImage>> splitBufferImgesMap(){
        Map<Integer,BufferedImage> map= new DiscernALLImg().imgFixedProcess();
        ArrayList<BufferedImage> bufferList = new ArrayList<>();
        for(Integer i:map.keySet()){
            bufferList.add(map.get(i));
        }
        System.out.println(bufferList.size());
        long max=Math.round((bufferList.size()/5)+0.4);
        return  Lists.partition(bufferList,15);
    }

    public ArrayList<String> getOneCompanyData() {
        return oneCompanyData;
    }
}
