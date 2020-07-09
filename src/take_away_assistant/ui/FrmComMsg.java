package take_away_assistant.ui;


import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import take_away_assistant.util;
import take_away_assistant.bean.BeanUser;
import take_away_assistant.others.BaseException;




public class FrmComMsg extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	
	private JLabel labelID = new JLabel          ("ID：                  ");
	private JLabel labelName = new JLabel        ("姓名：               ");
	private JLabel labelSex = new JLabel         ("性别：                ");
	private JLabel labelTel = new JLabel         ("手机号码：        ");
	private JLabel labelMail = new JLabel        ("邮箱：                ");
	private JLabel labelCity = new JLabel        ("所在城市：        ");
	private JLabel labelRegisterTime = new JLabel("注册时间：        ");
	private JLabel labelVip = new JLabel         ("是否为会员：    ");
	private JLabel labelVipdeadline = new JLabel ("会员截止日期：");
	
	
	private JLabel labelID1 = new JLabel(BeanUser.currentLoginUser.getUser_id());
	private JTextField edtName = new JTextField(20);
	private JTextField edtSex = new JTextField(20);
	private JLabel labelTel1=new JLabel(BeanUser.currentLoginUser.getcustomer_tel());
	private JTextField edtMail = new JTextField(20);
	private JTextField edtCity = new JTextField(20);
	String redatetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(BeanUser.currentLoginUser.getRegister_time());  // 获取年月日时分秒
	private JLabel labelRegisterTime1 = new JLabel(redatetime);
	private JLabel labelVip1 = new JLabel("是会员 ");
	private JLabel labelVipdeadline1 = new JLabel("");
			
	public FrmComMsg(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
		toolBar.add(this.btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		btnOk.setSize(200,100);
		workPane.setLayout(new GridLayout(9,2));
		
		String ifvip = new String();
		if(BeanUser.currentLoginUser.getVip()=="是"){
			String deadlinedatetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(BeanUser.currentLoginUser.getvip_deadline());
			ifvip="是";
			labelVipdeadline1.setText(deadlinedatetime.substring(0,10));
		} 
		else ifvip="不是";
		
		labelVip1.setText(ifvip);
		
		labelVip1.setText(ifvip);
		workPane.add(labelID);
		workPane.add(labelID1);
		workPane.add(labelName);
		workPane.add(edtName);
		workPane.add(labelSex);
		workPane.add(edtSex);
		workPane.add(labelTel);
		workPane.add(labelTel);
		workPane.add(labelMail);
		workPane.add(edtMail);
		workPane.add(labelCity);
		workPane.add(edtCity);
		workPane.add(labelRegisterTime);
		workPane.add(labelRegisterTime1);
		workPane.add(labelVip);
		workPane.add(labelVip1);
		workPane.add(labelVipdeadline);
		workPane.add(labelVipdeadline1);

		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(350, 330);
		this.btnCancel.addActionListener(this);
		this.btnOk.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel)
			this.setVisible(false);
		else if(e.getSource()==this.btnOk){
			try {
				util.userManager.comMsg(BeanUser.currentLoginUser,new String(edtName.getText()),new String(edtSex.getText()),
						new String(edtMail.getText()),new String(edtCity.getText()));
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		
	}

}
