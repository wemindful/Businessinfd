package domain;

import com.xuxueli.poi.excel.annotation.ExcelField;
import com.xuxueli.poi.excel.annotation.ExcelSheet;
import org.apache.poi.hssf.util.HSSFColor;

@ExcelSheet(name="网店工商信息表",headColor = HSSFColor.HSSFColorPredefined.LIGHT_GREEN)
public class ShopNetDTO {

    @ExcelField(name = "企业名称")
    private String companyName;

    @ExcelField(name = "企业注册号",width = 20)
    private String companyRegisterId;

    public ShopNetDTO(String companyName, String companyRegisterId) {
        this.companyName = companyName;
        this.companyRegisterId = companyRegisterId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyRegisterId() {
        return companyRegisterId;
    }

    public void setCompanyRegisterId(String companyRegisterId) {
        this.companyRegisterId = companyRegisterId;
    }

    @Override
    public String toString() {
        return "ShopNetDTO{" +
                "companyName='" + companyName + '\'' +
                ", companyRegisterId='" + companyRegisterId + '\'' +
                '}';
    }
}
