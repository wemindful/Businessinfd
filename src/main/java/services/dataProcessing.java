package services;

import com.xuxueli.poi.excel.ExcelExportUtil;
import domain.ShopNetDTO;
import main.AllConfig;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @program: businessinformationdiscern
 * @description: 元素识别数据清洗
 * @author: Mr.Dai
 * @create: 2018-07-19 21:00
 **/
public class dataProcessing {

    private  ArrayList<String> companyNamelist = new ArrayList<>();
    private  ArrayList<String> companyIdlist = new ArrayList<>();


    /**
     * 提取企业注册号 企业名称
     * @param srcMessageData 元素数据字符串
     */
    private void processFindString(String srcMessageData){
          srcMessageData=srcMessageData.trim();
          String[] temp = srcMessageData.split("\n");
          for (int i = 0; i < temp.length; i++) {
              System.out.println(temp[i]);
              String companyId="";
              String companyName = "";
              if(temp[i].contains("企业注册号")){
                  companyId=temp[i].substring(temp[i].indexOf(":")+1).trim();
                  companyId=redressMessageData(companyId);
                  companyIdlist.add(companyId);
                  //System.out.println(companyId);
              }else if(temp[i].contains("企业名称")){
                  companyName=temp[i].substring(temp[i].indexOf(":")+1).trim();
                  //System.out.println(companyName);
                  companyNamelist.add(companyName);
              }
              //System.out.println(companyName+" : "+companyId);
          }
      }


      public void finalProcess(){
          ImagesMegToText main= new ImagesMegToText();
          main.StartDiscern();
          ArrayList<String> strData=main.getOneCompanyData();
          for (int i = 0; i < strData.size(); i++) {
              System.out.println(strData.get(i));
              processFindString(strData.get(i));
          }
          //导出excel
          ArrayList<ShopNetDTO> objects = new ArrayList<>();;
          for (int i = 0; i < companyIdlist.size(); i++) {
              ShopNetDTO dto = new ShopNetDTO(companyNamelist.get(i), companyIdlist.get(i));
              objects.add(dto);
          }
          ExcelExportUtil.exportToFile(AllConfig.excel, objects);
          try {
              Desktop.getDesktop().open(new File(AllConfig.basepath));
          } catch (IOException e) {
              e.printStackTrace();
              System.out.println("无法打开文件目录");
          }
      }

    /**
     * 数据矫正算法
     */
    public String redressMessageData(String cname){
        if (cname != null && cname.contains(")")) {
            cname = cname.replace(")", "J");
        }
        return cname;
    }

}
