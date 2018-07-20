import org.junit.Test;

/**
 * @program: businessinformationdiscern
 * @description:
 * @author: Mr.Dai
 * @create: 2018-07-19 21:06
 **/
public class dataProcessing {


    private String str="企业注册号 : 913302055612570177\n" +
            "\n" +
            "企业名称 : 宁波中哲慕尚电子商务有限公司\n" +
            "\n" +
            "类 ”型 : 有限责任公司(法人独资)\n" +
            "\n" +
            "住 “所 : 宁波市江北长兴路689弄22号11幢A112室\n" +
            "\n" +
            "法定代表人 : 杨和荣\n" +
            "\n" +
            "成立时间 : 2010-08-26\n" +
            "\n" +
            "注册资本 : 1000万人民币元\n" +
            "\n" +
            "营业期限 : 2010-08-26至2020-08-25\n" +
            "\n" +
            "经营范围 : 服装、箱包、装帽、服饰的批发、零售、网上批发、零售及相关信息的咨询 ; 服装设计\n" +
            "、企业品牌管理、广告服务、企业管理咨询。\n" +
            "\n" +
            "登记机关 : 浙江省宁波市江北区工商行政管理局\n" +
            "\n" +
            "核准时间 : 2015-12-24\n";

    @Test
    public void test() {
        str=str.trim();
        String[] temp = str.split("\n");
        for (int i = 0; i < temp.length; i++) {
            //System.out.println(temp[i]);
            if(temp[i].contains("企业注册号")){
                System.out.println(temp[i].substring(temp[i].indexOf(":")+1).trim());
            }else if(temp[i].contains("企业名称")){
                System.out.println(temp[i].substring(temp[i].indexOf(":")+1).trim());
            }
        }

    }
}
