package take_away_assistant.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import take_away_assistant.util;
import take_away_assistant.bean.BeanUser;
import take_away_assistant.others.BaseException;



public class FrmRegister extends JDialog implements ActionListener {
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private JButton btnOk = new JButton("ע��");
	private JButton btnCancel = new JButton("ȡ��");
	
	private JLabel labelUserType=new JLabel("�û����ͣ�   ");
	private JLabel labelUser = new JLabel  ("ע���û���   ");
	private JLabel labelTel = new JLabel   ("�ֻ����룺   ");
	private JLabel labelPwd = new JLabel   ("�������룺   ");
	private JLabel labelPwd2 = new JLabel  ("ȷ�����룺   ");
	private JTextField edtUserId = new JTextField(20);
	private JTextField edtTel = new JTextField(20);
	private JPasswordField edtPwd = new JPasswordField(20);
	private JPasswordField edtPwd2 = new JPasswordField(20);
	public JRadioButton s1=new JRadioButton("�˿�           ",true);
	public JRadioButton s2=new JRadioButton("����Ա       ");
	public ButtonGroup userType=new ButtonGroup();
	
	public FrmRegister(Dialog f, String s, boolean b) {
		super(f, s, b);//���û����е�ĳһ�����캯��
		toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
		toolBar.add(this.btnOk);
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
		workPane.add(labelTel);
		workPane.add(edtTel);
		workPane.add(labelPwd);
		workPane.add(edtPwd);
		workPane.add(labelPwd2);
		workPane.add(edtPwd2);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(335, 240);
		this.btnCancel.addActionListener(this);//ÿһ��this������� ����ʵ��ActionListener����ӿ�
		this.btnOk.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnCancel)
			this.setVisible(false);
		else if(e.getSource()==this.btnOk){
			//��ȡ�˺�����
			String userid=this.edtUserId.getText();
			String tel=this.edtTel.getText();
			String pwd1=new String(this.edtPwd.getPassword());
			String pwd2=new String(this.edtPwd2.getPassword());
			 try{
				BeanUser user=util.userManager.reg(userid,tel,pwd1,pwd2);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
			
		}
	}

}
