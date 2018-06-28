package domain;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * @program: businessinformationdiscern
 * @description: 网店工商的信息的表格模型
 * @author: Mr.Dai
 * @create: 2018-06-25 16:18
 **/
public class ShopTableModel extends AbstractTableModel {

    //标题
    private static final String [] titleNames=new String[]{"企业注册号","企业名称"};

    //内容
    private  ArrayList<ShopNetDTO> data=new ArrayList<>();

    //是否可编辑
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public String getColumnName(int column) {
        return titleNames[column];
    }

    @Override
    public int getColumnCount() {
        return titleNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ShopNetDTO dto = data.get(rowIndex);
        if (0 == columnIndex)
            return dto.getCompanyRegisterId();
        if (1 == columnIndex)
            return dto.getCompanyName();
        return null;
    }

    public ArrayList<ShopNetDTO> getData() {
        return data;
    }

    public void setData(ArrayList<ShopNetDTO> data) {
        this.data = data;
    }
}
