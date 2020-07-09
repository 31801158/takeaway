package take_away_assistant.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import take_away_assistant.util;
import take_away_assistant.bean.BeanClass;
import take_away_assistant.bean.BeanGoods;
import take_away_assistant.bean.BeanShop;
import take_away_assistant.bean.BeanUser;
import take_away_assistant.others.BaseException;
import take_away_assistant.others.BusinessException;
import take_away_assistant.others.DbException;



public class FrmMain extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JMenuBar menubar=new JMenuBar(); 
	private JMenu menu_take=new JMenu("��ҳ");
    private JMenu menu_morder=new JMenu("�ҵĶ���");
    private JMenu menu_maddr=new JMenu("��ַ����");
    private JMenu menu_more=new JMenu("����");
    
    
    private JMenuItem  menuItem_take=new JMenuItem("ȥ������");
    private JMenuItem  menuItem_sorder=new JMenuItem("��������");
    //private JMenuItem  menuItem_DeleteOrder=new JMenuItem("ɾ������");
    private JMenuItem  menuItem_sTicket=new JMenuItem("�鿴�Ż�ȯ");
    private JMenuItem  menuItem_sAddr=new JMenuItem("�鿴�ջ���ַ");
    private JMenuItem  menuItem_addAddr=new JMenuItem("�����ջ���ַ");
    private JMenuItem  menuItem_deleteAddr=new JMenuItem("ɾ���ջ���ַ");
    private JMenuItem  menuItem_updateAddr=new JMenuItem("�����ջ���ַ");
    private JMenuItem  menuItem_sMsg=new JMenuItem("�鿴��Ϣ");
    private JMenuItem  menuItem_comMsg=new JMenuItem("������Ϣ");
    private JMenuItem  menuItem_modifyPwd=new JMenuItem("�޸�����");
    private JMenuItem  menuItem_addcart=new JMenuItem("���빺�ﳵ");
    
    private FrmLogin dlgLogin=null;
	private JPanel statusBar = new JPanel();
	
	private Object tblShopTitle[]=BeanShop.tableTitles;//��ͷ
	private Object tblShopData[][];//���̼ң�������Ϣ
	DefaultTableModel tabShopModel=new DefaultTableModel();//DefaultTableModel��ָĬ�ϵı����ģ�� ������������ JTBALE ��JTABLE��GETTABLEMODEL���õ�һ�������ģ�������� JTBALE
	private JTable dataTableShop=new JTable(tabShopModel);//TableModel��JTable��ģ���൱������һ��洢���ݶ���TableModelȥ�洢.�����,�ŵ�JTable��ȥ
	
	
	private Object tblClassTitle[]=BeanClass.tblClassTitle;
	private Object tblClassData[][];
	DefaultTableModel tabClassModel=new DefaultTableModel();
	private JTable dataTableClass=new JTable(tabClassModel);
	private Object tblGoodsTitle[]=BeanGoods.tblGoodsTitle;//BeanClass.name;
	private Object tblGoodsData[][];
	DefaultTableModel tabGoodsModel=new DefaultTableModel();
	private JTable dataTableGoods=new JTable(tabGoodsModel);
	
	
	
	private BeanShop curShop=null;
	private BeanClass curClass=null;
	List<BeanShop> allShop=null;
	List<BeanClass> shopClass=null;
	List<BeanGoods> classGoods=null;
	private void reloadShopTabel() {
		try {
			allShop=util.shopManager.loadAll();//�г�����shop ��һ���б�
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		//�������
		tblShopData=new Object[allShop.size()][BeanShop.tableTitles.length];
		for(int i=0;i<allShop.size();i++){//��������  ����ÿһplan
			for(int j=0;j<BeanShop.tableTitles.length;j++)
				try {
					tblShopData[i][j]=allShop.get(i).getCell(j);
				} catch (DbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//����ÿһ����Ԫ��
		}
		//����ģ��
		tabShopModel.setDataVector(tblShopData,tblShopTitle);//�������ݺͱ�ͷ
		this.dataTableShop.validate();//ȷ�����������Ч�Ĳ���   
		this.dataTableShop.repaint();//(ˢ��)
	}
	
	
	protected void reloadClassTabel(int shopIdx) {
		if(shopIdx<0) return;
		curShop=allShop.get(shopIdx);
		try {
			shopClass=util.classManager.loadClass(curShop);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblClassData =  new Object[shopClass.size()][BeanClass.tblClassTitle.length];
		for(int i=0;i<shopClass.size();i++){
			for(int j=0;j<BeanClass.tblClassTitle.length;j++){
				tblClassData[i][j]=shopClass.get(i).getCell(j);
				}
		}
		tabClassModel.setDataVector(tblClassData,tblClassTitle);
		this.dataTableClass.validate();
		this.dataTableClass.repaint();
		
	}
	
	protected void reloadGoodsTabel(int classIdx) {
		if(classIdx<0) return;
		curClass=shopClass.get(classIdx);
		try {
			classGoods=util.goodsManager.loadGoods(curClass);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblGoodsData =new Object[classGoods.size()][BeanGoods.tblGoodsTitle.length];
		for(int i=0;i<classGoods.size();i++){
			for(int j=0;j<(BeanGoods.tblGoodsTitle.length);j++)
				tblGoodsData[i][j]=classGoods.get(i).getCell(j);
		}
		
		tabGoodsModel.setDataVector(tblGoodsData,tblGoodsTitle);
		this.dataTableGoods.validate();
		this.dataTableGoods.repaint();
	}
	
	
	
	public FrmMain(){
			
		//��ʼ���
		this.setExtendedState(Frame.MAXIMIZED_BOTH);//public int getExtendedState()��ȡ�˴����״̬����״̬��ʾΪ��λ���롣 
		this.setTitle("��������");
		dlgLogin=new FrmLogin(this,"��½",true);
		dlgLogin.setVisible(true);
		//�˵�
		this.menu_take.add(this.menuItem_take);this.menuItem_take.addActionListener(this);
		this.menu_morder.addActionListener(this);
		this.menu_morder.add(this.menuItem_sorder);this.menuItem_sorder.addActionListener(this);
		//this.menu_morder.add(this.menuItem_DeleteOrder);this.menuItem_DeleteOrder.addActionListener(this);
		this.menu_maddr.add(this.menuItem_sAddr);this.menuItem_sAddr.addActionListener(this);
		this.menu_maddr.add(this.menuItem_addAddr);this.menuItem_addAddr.addActionListener(this);
		this.menu_maddr.add(this.menuItem_deleteAddr);this.menuItem_deleteAddr.addActionListener(this);
		this.menu_maddr.add(this.menuItem_updateAddr);this.menuItem_updateAddr.addActionListener(this);
		this.menu_more.add(this.menuItem_sTicket);this.menuItem_sTicket.addActionListener(this);
		this.menu_more.add(this.menuItem_sMsg);this.menuItem_sMsg.addActionListener(this);
		this.menu_more.add(this.menuItem_comMsg);this.menuItem_comMsg.addActionListener(this);
		this.menu_more.add(this.menuItem_modifyPwd);this.menuItem_modifyPwd.addActionListener(this);
		this.menuItem_addcart.addActionListener(this);
		
		menubar.add(menu_take);
		menubar.add(menu_morder);
		menubar.add(menu_maddr);
		menubar.add(menu_more);
		menubar.add(menuItem_addcart);
		
		this.setJMenuBar(menubar);
		
		this.getContentPane().add(new JScrollPane(this.dataTableShop), BorderLayout.WEST);
		//this.getContentPane()�������ǳ�ʼ��һ�����������������������һЩ�ؼ�
		//�г���Ʒ��
		
	    this.dataTableShop.addMouseListener(new MouseAdapter (){

			@Override
			public void mouseClicked(MouseEvent e) {//�г��ұߵ�һ���̵��Ӧ�����Ʒ��
				int i=FrmMain.this.dataTableShop.getSelectedRow();
				if(i<0) {
					return;
				}
				FrmMain.this.reloadClassTabel(i);
			}
	    	
	    });
	    this.getContentPane().add(new JScrollPane(this.dataTableClass), BorderLayout.CENTER);
	    
	    this.reloadShopTabel();
	    
	    //�г���Ʒ
	    this.dataTableClass.addMouseListener(new MouseAdapter (){

			@Override
			public void mouseClicked(MouseEvent e) {
				int i=FrmMain.this.dataTableClass.getSelectedRow();
				if(i<0) {
					return;
				}
				FrmMain.this.reloadGoodsTabel(i);
			}
	    	
	    });
	    this.getContentPane().add(new JScrollPane(this.dataTableGoods), BorderLayout.EAST);
	    
	    
	  //״̬��
	    statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
	    String a="Hi ���� !";
	    //String b=BeanUser.currentLoginUser.getUser_id();
	    JLabel label=new JLabel(a);//�޸ĳ�   ���ã�+��½�û���
	    statusBar.add(label);
	    this.getContentPane().add(statusBar,BorderLayout.SOUTH);
	    this.addWindowListener(new WindowAdapter(){   
	    	public void windowClosing(WindowEvent e){ 
	    		System.exit(0);
             }
        });
	    if(BeanUser.currentLoginUserType==0)
	    this.setVisible(true);
	}
	

	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.menuItem_take){
			this.setVisible(false);
			this.setVisible(true);
			
		}
		else if(e.getSource()==this.menuItem_addcart){
			int i=FrmMain.this.dataTableGoods.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "��ѡ��Ҫ�ӹ�����Ʒ", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			
		}
		else if(e.getSource()==this.menuItem_sorder){
			FrmOrders frm=new FrmOrders();
			frm.setVisible(true);
			
		}
		else if(e.getSource()==this.menuItem_sMsg){
			FrmSMsg dlg=new FrmSMsg(this,"�鿴��Ϣ",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_comMsg){
				FrmComMsg dlg=new FrmComMsg(this,"������Ϣ",true);
				dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_modifyPwd){
			FrmModifyPwd dlg=new FrmModifyPwd(this,"�����޸�",true);
			dlg.setVisible(true);
		}
	   
	}

}
