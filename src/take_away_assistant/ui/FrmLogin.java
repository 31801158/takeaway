package take_away_assistant.ui;
import take_away_assistant.util;
import take_away_assistant.bean.BeanUser;
import take_away_assistant.others.BaseException;
import take_away_assistant.ui.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;







public class FrmLogin extends JDialog implements ActionListener {
	
		private JPanel toolBar = new JPanel();
		private JPanel workPane = new JPanel();
		private JButton btnLogin = new JButton("��½");
		private JButton btnRegister = new JButton("ע��");
		private JButton btnCancel = new JButton("ȡ��");
		private JLabel labelUser=new JLabel    ("�û�����   ");
		private JLabel labelPwd=new JLabel     ("���룺       ");
		private JLabel labelUserType=new JLabel("�û����ͣ�");
		private JTextField edtUserId=new JTextField(20);
		private JPasswordField edtPwd=new JPasswordField(20);
		public JRadioButton s1=new JRadioButton("�˿�           ",true);
		public JRadioButton s2=new JRadioButton("����Ա       ");
		public ButtonGroup userType=new ButtonGroup();
		
		public FrmLogin(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
		toolBar.add(this.btnRegister);
		toolBar.add(btnLogin);
		toolBar.add(btnCancel);
		
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		workPane.add(labelUserType);
		userType.add(s1);
		userType.add(s2);
		workPane.add(s1);
		workPane.add(s2);
		workPane.add(labelUser);
		workPane.add(edtUserId);
		workPane.add(labelPwd);
		workPane.add(edtPwd);
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(330, 175);
		// ��Ļ������ʾ
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		btnLogin.addActionListener(this);//����ʵ����ActionListener�ӿڣ�Ҳ����д��ActionListener�涨�ķ���
		btnCancel.addActionListener(this);
		this.btnRegister.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnLogin) {
			String userid=this.edtUserId.getText();
			if(s1.isSelected())
				BeanUser.currentLoginUserType=0;
			else
				BeanUser.currentLoginUserType=1;
			String pwd=new String(this.edtPwd.getPassword());
			try {
				BeanUser.currentLoginUser= util.userManager.login(userid, pwd);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
			
			
		} else if (e.getSource() == this.btnCancel) {
			System.exit(0);
		} else if(e.getSource()==this.btnRegister){
			FrmRegister dlg=new FrmRegister(this,"ע��",true);
			dlg.setVisible(true);
			
		}
	}
	
	

	

}
