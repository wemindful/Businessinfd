package domain;

import javax.swing.table.AbstractTableModel;

/**
 * @program: businessinformationdiscern
 * @description: 网店工商的信息的表格模型
 * @author: Mr.Dai
 * @create: 2018-06-25 16:18
 **/
public class ShopTableModel extends AbstractTableModel {


    private static final String [] titleNames=new String[]{"企业注册号","企业名称"};

    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return titleNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }
}
