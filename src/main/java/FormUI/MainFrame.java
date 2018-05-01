package FormUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel dModel;
	private final String [] columnNames =new String[] {
			"\u7F16\u53F7", "\u4F01\u4E1A\u540D\u79F0", "\u4F01\u4E1A\u6CE8\u518C\u53F7"
		};
	String [][]tableVales={{"A1","B1"},{"A2","B2"},{"A3","B3"},{"A4","B4"},{"A5","B5"}}; //数据  
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
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
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 855, 584);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(48, 10, 753, 435);
		contentPane.add(scrollPane);
		
		table = new JTable();
		dModel = new DefaultTableModel(tableVales,columnNames);  
		table.setFont(new Font("宋体", Font.PLAIN, 15));
		scrollPane.setViewportView(table);
	}
}