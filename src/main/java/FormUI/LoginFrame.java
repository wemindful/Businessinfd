package FormUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame {

	private JFrame frame;
	private JPasswordField pfUserPasswd;
	private JTextField tFUserName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame window = new LoginFrame();
					window.frame.setLocationRelativeTo(null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("");
		frame.setResizable(false);
		frame.getContentPane().setEnabled(false);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u8D26\u53F7");
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 28));
		lblNewLabel.setBounds(93, 88, 71, 48);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u5BC6\u7801");
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 28));
		lblNewLabel_1.setBounds(93, 146, 71, 67);
		frame.getContentPane().add(lblNewLabel_1);
		
		pfUserPasswd = new JPasswordField();
		pfUserPasswd.setBounds(174, 167, 214, 38);
		frame.getContentPane().add(pfUserPasswd);
		
		tFUserName = new JTextField();
		tFUserName.setBounds(174, 103, 214, 40);
		frame.getContentPane().add(tFUserName);
		tFUserName.setColumns(10);
		
		JButton btnLogin = new JButton("\u8FDB\u5165");
		btnLogin.addActionListener(new loginAction(tFUserName,pfUserPasswd));
		btnLogin.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		btnLogin.setBounds(101, 272, 93, 48);
		frame.getContentPane().add(btnLogin);
		
		JButton btnExite = new JButton("\u9000\u51FA");
		btnExite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExite.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		btnExite.setBounds(334, 272, 93, 48);
		frame.getContentPane().add(btnExite);
		frame.setTitle("\u667A\u80FD\u5DE5\u5546\u8BC6\u522B\u7CFB\u7EDF\u767B\u5F55");
		frame.setBounds(100, 100, 505, 401);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
