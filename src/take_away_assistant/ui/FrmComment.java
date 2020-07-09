package take_away_assistant.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class FrmComment extends JDialog implements ActionListener{
	public static List<String> comment=new ArrayList<String>();
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private JButton btnOk = new JButton("ȷ��");
	private JButton btnCancel = new JButton("ȡ��");
	private JLabel labelGoodsstar=new JLabel("��Ʒ�Ǽ�");
	public JRadioButton s1=new JRadioButton("1��");
	public JRadioButton s2=new JRadioButton("2��");
	public JRadioButton s3=new JRadioButton("3��");
	public JRadioButton s4=new JRadioButton("4��");
	public JRadioButton s5=new JRadioButton("5��",true);
	private JLabel labelGoods=new JLabel("��Ʒ����");
	private JTextField edtGoods=new JTextField(20);
	
	private JLabel labelRiderstar=new JLabel("��������");
	public JRadioButton r1=new JRadioButton("����");
	public JRadioButton r2=new JRadioButton("����",true);
	
	
	public ButtonGroup group1=new ButtonGroup();
	public ButtonGroup group2=new ButtonGroup();
	
	public FrmComment(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
		toolBar.add(this.btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		workPane.add(labelGoodsstar);
		group1.add(s1);
		group1.add(s2);
		group1.add(s3);
		group1.add(s4);
		group1.add(s5);
		workPane.add(s1);
		workPane.add(s2);
		workPane.add(s3);
		workPane.add(s4);
		workPane.add(s5);
		
		workPane.add(labelGoods);
		workPane.add(edtGoods);
		
		workPane.add(labelRiderstar);
		group2.add(r1);
		group2.add(r2);
		workPane.add(r1);
		workPane.add(r2);
		

		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(350, 175);
		// ��Ļ������ʾ
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		btnOk.addActionListener(this);//����ʵ����ActionListener�ӿڣ�Ҳ����д��ActionListener�涨�ķ���
		btnCancel.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnCancel) {
			this.setVisible(false);
			return;
		} 
		else if(e.getSource()==this.btnOk){
			String goodscomment=this.edtGoods.getText();
			String goodsstar;
			String riderstar;
			Timestamp commenttime=new java.sql.Timestamp(System.currentTimeMillis());
			
			if(s1.isSelected())
				goodsstar="1";
			else if(s2.isSelected())
				goodsstar="2";
			else if(s3.isSelected())
				goodsstar="3";
			else if(s4.isSelected())
				goodsstar="4";
			else goodsstar="5";
			
			if(r1.isSelected())
				riderstar="����";
			else riderstar="����";
			comment.add(goodscomment);
			comment.add(goodsstar);
			comment.add(riderstar);
			comment.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(commenttime));
			this.setVisible(false);
		}
		
	}
}
