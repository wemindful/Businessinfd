package FormUI;



import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @program: businessinformationdiscern
 * @description: 目录确认
 * @author: Mr.Dai
 * @create: 2018-06-25 22:43
 **/
public class DirectoryView extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel basePathLabel;//运行目录标签
	private JTextField basepathField;//基础目录
	private JTextField datapathField;//数据目录
	private JTextField dataimgField;//去水印目录
	private JTextField temppathField;//缓存目录
	private JButton btnChangeFile;
	private JButton btnDiscern;

    private  String path=null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DirectoryView frame = new DirectoryView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DirectoryView() {
		setType(Type.UTILITY);
		setTitle("请确保目录相对应");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 639, 430);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u5929\u732B\u5DE5\u5546\u4FE1\u606F\u6267\u7167");
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 80, 112, 44);
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(153, 102, 255));
		panel.setBounds(0, 0, 617, 26);
		contentPane.add(panel);
		
		basePathLabel = new JLabel("New label");
		basePathLabel.setHorizontalAlignment(SwingConstants.CENTER);
		basePathLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		basePathLabel.setBounds(78, 59, 434, 15);
		basePathLabel.setText("当前运行目录： "+System.getProperty("user.dir"));
		
		contentPane.add(basePathLabel);
		
		JLabel lblData = new JLabel("data");
		lblData.setFont(new Font("微软雅黑", Font.PLAIN, 17));
		lblData.setBounds(10, 144, 126, 33);
		contentPane.add(lblData);
		
		JLabel lblImages = new JLabel("images");
		lblImages.setFont(new Font("微软雅黑", Font.PLAIN, 17));
		lblImages.setBounds(10, 203, 126, 33);
		contentPane.add(lblImages);
		
		JLabel lblTemp = new JLabel("temp");
		lblTemp.setFont(new Font("微软雅黑", Font.PLAIN, 17));
		lblTemp.setBounds(10, 259, 126, 26);
		contentPane.add(lblTemp);
		
		basepathField = new JTextField();
		basepathField.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		basepathField.setBounds(142, 88, 400, 31);
		contentPane.add(basepathField);
		basepathField.setColumns(10);
		
		datapathField = new JTextField();
		datapathField.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		datapathField.setColumns(10);
		datapathField.setBounds(142, 151, 400, 31);
		contentPane.add(datapathField);
		
		dataimgField = new JTextField();
		dataimgField.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		dataimgField.setColumns(10);
		dataimgField.setBounds(142, 205, 400, 31);
		contentPane.add(dataimgField);
		
		temppathField = new JTextField();
		temppathField.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		temppathField.setColumns(10);
		temppathField.setBounds(142, 258, 400, 31);
		contentPane.add(temppathField);
		
		JLabel lblNewLabel_1 = new JLabel("\u8BF7\u786E\u5B9A\u4E0B\u9762\u7684\u76EE\u5F55\u90FD\u5EFA\u7ACB\u597D\u4E86\uFF0C\u8BC6\u522B\u76EE\u5F55\u662F\u5426\u6307\u5B9A\u51C6\u786E");
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(62, 36, 500, 15);
		contentPane.add(lblNewLabel_1);
		
		btnChangeFile = new JButton("\u5207\u6362");
		btnChangeFile.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnChangeFile.setBounds(552, 83, 65, 38);
		contentPane.add(btnChangeFile);
		
		btnDiscern = new JButton("\u786E\u5B9A(Enter\uFF09");
		btnDiscern.setFont(new Font("微软雅黑", Font.PLAIN, 17));
		btnDiscern.setBounds(170, 315, 264, 50);
		contentPane.add(btnDiscern);
		
		//监听器
		btnChangeFile.addActionListener(this);
        btnDiscern.addActionListener(this);
		//初始化目录
		initDiatory("ss");
		
	}

	private void initDiatory(String property) {
		property = System.getProperty("user.dir");
		basepathField.setText(property+"\\天猫工商信息执照");
		datapathField.setText(property+"\\data");
		dataimgField.setText(property+"\\images");
		temppathField.setText(property+"\\temp\\");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnChangeFile) {
			 JFileChooser jfc=new JFileChooser(new File(System.getProperty("user.dir")));  
		        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  
		        jfc.showDialog(new JLabel(), "选择");  
		        File file=jfc.getSelectedFile();
		        if(file.isDirectory()){  
		            System.out.println("文件夹:"+file.getAbsolutePath()); 
		            path = file.getPath();
		            basepathField.setText(path+"\\天猫工商信息执照");
		    		datapathField.setText(path+"\\data");
		    		dataimgField.setText(path+"\\images");
		    		temppathField.setText(path+"\\temp\\");
		        }else if(file.isFile()){
		          return;
		        }  
		        System.out.println(jfc.getSelectedFile().getName());  
		}else if(e.getSource()==btnDiscern) {
			this.dispose();
			AppUi.basrpath=path;
        }
	}
}
