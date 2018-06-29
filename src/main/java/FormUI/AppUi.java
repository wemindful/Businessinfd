package FormUI;

import domain.ShopTableModel;
import services.discernShopImg;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: businessinformationdiscern
 * @description: 界面入口
 * @author: Mr.Dai
 * @create: 2018-06-25 22:43
 **/
public class AppUi extends  JFrame implements ActionListener,TableModelListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JDesktopPane desktopPane;
    private final JMenuItem abortMenuItem; // 关于
    private final JMenuItem startMenuItem;// 开始识别
    private final JTable table;  //数据表
    private final JToolBar toolBar; // 可移动工具栏
    private final JButton btnStartRed; // 开始识别
    private static JScrollPane scrollPane; //滚动面板
    private final JProgressBar progressBar;//识别进度条
    private JButton btnInsertData;
    private JButton btnQueryData;
    private static boolean flag=false;
    public static String basrpath="D:\\businessinformationdiscern_jar";

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AppUi frame = new AppUi();
                    frame.setVisible(true);
                    // table大小变化
                    frame.addComponentListener(new ComponentAdapter() {
                        @Override
                        public void componentResized(ComponentEvent e) {
                            int width=e.getComponent().getWidth();
                            int height=e.getComponent().getHeight();
                            scrollPane.setSize(width-30, height-100);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public AppUi() {
        this.setTitle("网店工商信息图片文字提取");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 876, 585);

        JMenuBar JMenuBar = new JMenuBar();
        JMenuBar.setForeground(new Color(153, 0, 255));
        JMenuBar.setMargin(new Insets(0, 5, 5, 5));
        setJMenuBar(JMenuBar);

        JMenu mnNewMenu = new JMenu("\u6587\u4EF6");
        JMenuBar.add(mnNewMenu);

        JMenu mnNewMenu_1 = new JMenu("\u529F\u80FD");
        JMenuBar.add(mnNewMenu_1);

        startMenuItem = new JMenuItem("\u8BBE\u7F6E\u76EE\u5F55");
        mnNewMenu_1.add(startMenuItem);

        JMenu helpMenu = new JMenu("\u5E2E\u52A9");
        JMenuBar.add(helpMenu);

        abortMenuItem = new JMenuItem("\u5173\u4E8E");
        helpMenu.add(abortMenuItem);

        toolBar = new JToolBar();
        JMenuBar.add(toolBar);

        btnStartRed = new JButton("\u5F00\u59CB\u8BC6\u522B");
        toolBar.add(btnStartRed);

        btnInsertData = new JButton("\u5BFC\u5165\u6570\u636E\u5E93");
        toolBar.add(btnInsertData);

        btnQueryData = new JButton("\u67E5\u8BE2\u6570\u636E");
        toolBar.add(btnQueryData);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(12, 12, 836, 486);
        contentPane.add(scrollPane);

        table = new JTable();
        setFont(new Font("微软雅黑", Font.PLAIN, 14));
        scrollPane.setViewportView(table);

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setForeground(Color.RED);
        JMenuBar.add(progressBar);

        desktopPane = new JDesktopPane();
        desktopPane.setBounds(0, 0, 716, 461);

        // 统一监听器
        abortMenuItem.addActionListener(this);
        startMenuItem.addActionListener(this);
        btnStartRed.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == abortMenuItem) {
            JDialog dialog = new JDialog();
            dialog.setTitle("关于");
            dialog.setBounds(this.getX(), this.getY(), 150, 150);
            dialog.setLocationRelativeTo(this);
            Container pane = dialog.getContentPane();
            pane.setLayout(new FlowLayout());
            JTextArea message = new JTextArea();
            message.setText("网店工商信息" + "\n" + "武生院总队");
            pane.add(message);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } else if (e.getSource() == startMenuItem) {
            //路径设置
            DirectoryView view = new DirectoryView();
            view.setVisible(true);
        }else if(e.getSource()==btnStartRed){
            //开始识别
            ShopTableModel model=new ShopTableModel();
            ExecutorService exe=Executors.newFixedThreadPool(1);
            exe.execute(() -> {
                table.setModel(model);
                table.updateUI();
                discernShopImg shopImg = new discernShopImg(basrpath,"gui");
                try {
                    shopImg.initEnvironment();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                progressBar.setMaximum(shopImg.getImageNumber());
                final int x=shopImg.getImageNumber();
                //处理progressBar 相关EDT问题
                new SwingWorker<Boolean, Integer>() {
                    @Override
                    protected Boolean doInBackground() throws Exception {
                        int max=0;
                        int l=x;
                        while (l>0){
                            l--;
                            publish(max++);
                            Thread.sleep(600);
                        }
                        return null;
                    }
                    @Override
                    protected void process(List<Integer> chunks) {
                        progressBar.setValue(chunks.get(chunks.size()-1));
                        progressBar.updateUI();
                    }
                }.execute();
                shopImg.StartDiscern();
                //更新数据
                model.setData(shopImg.getObjects());
                table.updateUI();
            });
            exe.shutdown();
            new Thread(() -> {
                while (true){
                    if(exe.isTerminated()){
                        break;
                    }
                }
            }).start();
        }
        JOptionPane.showMessageDialog(this,"请等待识别完成...最终结果自动显示！");

    }

    @Override
    public void tableChanged(TableModelEvent e) {
        if(e.getType()==TableModelEvent.INSERT){
            System.out.println(e.getColumn());
        }
    }
}
