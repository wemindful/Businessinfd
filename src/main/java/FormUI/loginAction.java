package FormUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class loginAction implements ActionListener {

	private JPasswordField pfUserPasswd;
	private JTextField tFUserName;
	
	
	
	
	public loginAction(JTextField tFUserName2, JPasswordField tFUserName) {
		super();
		this.pfUserPasswd = tFUserName;
		this.tFUserName = tFUserName2;
	}




	@Override
	public void actionPerformed(ActionEvent e) {
		 char[] ps = pfUserPasswd.getPassword();
		 String passwd = new String(ps);
		 String username = tFUserName.getText().trim();
		 if ("admin".equals(username)&& "admin123".equals(passwd)) {
			
		}else {
			JOptionPane.showMessageDialog(null, "用户信息错误");  
		}
	}

}
