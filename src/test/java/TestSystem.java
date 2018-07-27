import org.junit.Test;

import java.util.Properties;

/**
 * @program: businessinformationdiscern
 * @description: 获取系统信息
 * @author: Mr.Dai
 * @create: 2018-07-26 23:15
 **/
public class TestSystem {

    @Test
    public  void getVersion(){
        Properties s=System.getProperties();
        s.list(System.out);
    }
}
