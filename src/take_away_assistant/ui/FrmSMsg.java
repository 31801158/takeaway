package take_away_assistant.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import take_away_assistant.bean.BeanUser;

public class FrmSMsg extends JDialog implements ActionListener{
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("ȷ��");
	private Button btnCancel = new Button("ȡ��");
	
	private JLabel labelID = new JLabel          ("ID��                  ");
	private JLabel labelName = new JLabel        ("������               ");
	private JLabel labelSex = new JLabel         ("�Ա�                ");
	private JLabel labelTel = new JLabel         ("�ֻ����룺        ");
	private JLabel labelMail = new JLabel        ("���䣺                ");
	private JLabel labelCity = new JLabel        ("���ڳ��У�        ");
	private JLabel labelRegisterTime = new JLabel("ע��ʱ�䣺        ");
	private JLabel labelVip = new JLabel         ("�Ƿ�Ϊ��Ա��    ");
	private JLabel labelVipdeadline = new JLabel ("��Ա��ֹ���ڣ�");
	
	
	private JLabel labelID1 = new JLabel(BeanUser.currentLoginUser.getUser_id());
	private JLabel labelName1 = new JLabel        ("");
	private JLabel labelSex1 = new JLabel         ("");
	private JLabel labelTel1 = new JLabel         (BeanUser.currentLoginUser.getcustomer_tel()); //(BeanUser.currentLoginUser.getcustomer_tel());
	private JLabel labelMail1 = new JLabel        ("");
	private JLabel labelCity1 = new JLabel        ("");
	String redatetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(BeanUser.currentLoginUser.getRegister_time());  // ��ȡ������ʱ����
	private JLabel labelRegisterTime1 = new JLabel(redatetime+"          ");
	private JLabel labelVip1 = new JLabel("�ǻ�Ա                                                ");
	private JLabel labelVipdeadline1 = new JLabel("                            ");
			
	public FrmSMsg(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
		toolBar.add(this.btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		btnOk.setSize(200,100);
		workPane.setLayout(new GridLayout(9,2));
		
		if(BeanUser.currentLoginUser.getcustomer_name()==null||"".equals(BeanUser.currentLoginUser.getUser_id()))
			labelName1.setText("δ����");
		else
			labelName1.setText(BeanUser.currentLoginUser.getcustomer_name());
		
		if(BeanUser.currentLoginUser.getcustomer_sex()==null||"".equals(BeanUser.currentLoginUser.getUser_id()))
			labelSex1.setText("δ����");
		else
			labelSex1.setText(BeanUser.currentLoginUser.getcustomer_sex());
		
		if(BeanUser.currentLoginUser.getcustomer_mail()==null||"".equals(BeanUser.currentLoginUser.getcustomer_mail()))
			labelMail1.setText("δ����");
		else
			labelMail1.setText(BeanUser.currentLoginUser.getcustomer_mail());
		if(BeanUser.currentLoginUser.getcustomer_city()==null||"".equals(BeanUser.currentLoginUser.getcustomer_city()))
			labelCity1.setText("δ����");
		else 
			labelCity1.setText(BeanUser.currentLoginUser.getcustomer_city());
		
		String ifvip = new String();
		if(BeanUser.currentLoginUser.getVip().equals("��")){
			String deadlinedatetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(BeanUser.currentLoginUser.getvip_deadline());
			ifvip="��vip";
			labelVipdeadline1.setText(deadlinedatetime.substring(0,10));//������10
		} 
		else ifvip="����";

		
		labelVip1.setText(ifvip);
		workPane.add(labelID);
		workPane.add(labelID1);
		workPane.add(labelName);
		workPane.add(labelName1);
		workPane.add(labelSex);
		workPane.add(labelSex1);
		workPane.add(labelTel);
		workPane.add(labelTel1);
		workPane.add(labelMail);
		workPane.add(labelMail1);
		workPane.add(labelCity);
		workPane.add(labelCity1);
		workPane.add(labelRegisterTime);
		workPane.add(labelRegisterTime1);
		workPane.add(labelVip);
		workPane.add(labelVip1);
		workPane.add(labelVipdeadline);
		workPane.add(labelVipdeadline1);

		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(400, 330);
		this.btnCancel.addActionListener(this);
		this.btnOk.addActionListener(this);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel)
			this.setVisible(false);
		
	}

}
