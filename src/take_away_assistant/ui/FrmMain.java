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
	private JMenu menu_take=new JMenu("首页");
    private JMenu menu_morder=new JMenu("我的订单");
    private JMenu menu_maddr=new JMenu("地址管理");
    private JMenu menu_more=new JMenu("更多");
    
    
    private JMenuItem  menuItem_take=new JMenuItem("去点外卖");
    private JMenuItem  menuItem_sorder=new JMenuItem("订单管理");
    //private JMenuItem  menuItem_DeleteOrder=new JMenuItem("删除订单");
    private JMenuItem  menuItem_sTicket=new JMenuItem("查看优惠券");
    private JMenuItem  menuItem_sAddr=new JMenuItem("查看收货地址");
    private JMenuItem  menuItem_addAddr=new JMenuItem("新增收货地址");
    private JMenuItem  menuItem_deleteAddr=new JMenuItem("删除收货地址");
    private JMenuItem  menuItem_updateAddr=new JMenuItem("更改收货地址");
    private JMenuItem  menuItem_sMsg=new JMenuItem("查看信息");
    private JMenuItem  menuItem_comMsg=new JMenuItem("完善信息");
    private JMenuItem  menuItem_modifyPwd=new JMenuItem("修改密码");
    private JMenuItem  menuItem_addcart=new JMenuItem("加入购物车");
    
    private FrmLogin dlgLogin=null;
	private JPanel statusBar = new JPanel();
	
	private Object tblShopTitle[]=BeanShop.tableTitles;//表头
	private Object tblShopData[][];//（商家）表中信息
	DefaultTableModel tabShopModel=new DefaultTableModel();//DefaultTableModel是指默认的表控制模型 它可以来控制 JTBALE 用JTABLE的GETTABLEMODEL来得到一个表控制模型来控制 JTBALE
	private JTable dataTableShop=new JTable(tabShopModel);//TableModel是JTable的模型相当与替身。一般存储数据都用TableModel去存储.存好了,放到JTable里去
	
	
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
			allShop=util.shopManager.loadAll();//列出所有shop 是一个列表
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		//做出表格
		tblShopData=new Object[allShop.size()][BeanShop.tableTitles.length];
		for(int i=0;i<allShop.size();i++){//放入数据  遍历每一plan
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
			//放入每一个单元格
		}
		//建出模型
		tabShopModel.setDataVector(tblShopData,tblShopTitle);//放入数据和表头
		this.dataTableShop.validate();//确保组件具有有效的布局   
		this.dataTableShop.repaint();//(刷新)
	}
	
	
	protected void reloadClassTabel(int shopIdx) {
		if(shopIdx<0) return;
		curShop=allShop.get(shopIdx);
		try {
			shopClass=util.classManager.loadClass(curShop);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
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
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
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
			
		//初始最大化
		this.setExtendedState(Frame.MAXIMIZED_BOTH);//public int getExtendedState()获取此窗体的状态。该状态表示为逐位掩码。 
		this.setTitle("外卖助手");
		dlgLogin=new FrmLogin(this,"登陆",true);
		dlgLogin.setVisible(true);
		//菜单
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
		//this.getContentPane()的作用是初始化一个容器，用来在容器上添加一些控件
		//列出商品类
		
	    this.dataTableShop.addMouseListener(new MouseAdapter (){

			@Override
			public void mouseClicked(MouseEvent e) {//列出右边的一个商店对应多个商品类
				int i=FrmMain.this.dataTableShop.getSelectedRow();
				if(i<0) {
					return;
				}
				FrmMain.this.reloadClassTabel(i);
			}
	    	
	    });
	    this.getContentPane().add(new JScrollPane(this.dataTableClass), BorderLayout.CENTER);
	    
	    this.reloadShopTabel();
	    
	    //列出商品
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
	    
	    
	  //状态栏
	    statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
	    String a="Hi 您好 !";
	    //String b=BeanUser.currentLoginUser.getUser_id();
	    JLabel label=new JLabel(a);//修改成   您好！+登陆用户名
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
				JOptionPane.showMessageDialog(null, "请选择要加购的商品", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			
		}
		else if(e.getSource()==this.menuItem_sorder){
			FrmOrders frm=new FrmOrders();
			frm.setVisible(true);
			
		}
		else if(e.getSource()==this.menuItem_sMsg){
			FrmSMsg dlg=new FrmSMsg(this,"查看信息",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_comMsg){
				FrmComMsg dlg=new FrmComMsg(this,"完善信息",true);
				dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_modifyPwd){
			FrmModifyPwd dlg=new FrmModifyPwd(this,"密码修改",true);
			dlg.setVisible(true);
		}
	   
	}

}
