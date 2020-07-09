package take_away_assistant.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;





import take_away_assistant.util;
import take_away_assistant.bean.BeanOrders;
import take_away_assistant.others.BaseException;
import take_away_assistant.others.BusinessException;
import take_away_assistant.others.DBUtil;
import take_away_assistant.others.DbException;

public class FrmOrders extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JMenuBar menubar=new JMenuBar(); 
	private JMenuItem  menuItem_show=new JMenuItem("显示所有订单");
    private JMenuItem  menuItem_delete=new JMenuItem("删除订单");
    private JMenuItem  menuItem_comment=new JMenuItem("去评价 > ");
    private JMenuItem  menuItem_return=new JMenuItem("返回主界面");
	private JPanel statusBar = new JPanel();
	
	private Object tblOrderTitle[]=BeanOrders.tblorderTitle;//表头
	private Object tblOrderData[][];//（商家）表中信息
	DefaultTableModel tabOrderModel=new DefaultTableModel();//DefaultTableModel是指默认的表控制模型 它可以来控制 JTBALE 用JTABLE的GETTABLEMODEL来得到一个表控制模型来控制 JTBALE
	private JTable dataTableOrder=new JTable(tabOrderModel);//TableModel是JTable的模型相当与替身。一般存储数据都用TableModel去存储.存好了,放到JTable里去
	

	
	private BeanOrders curOrder=null;
	public static List<BeanOrders> orders=null;//所有订单  一个列表
//	List<BeanClass> shopClass=null;
	
	private void reloadOrderTable() throws DbException, BusinessException{
		try {
			orders=util.ordersManager.loadOrders();//列出所有订单 是一个列表
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		//做出表格
		tblOrderData =  new Object[orders.size()][BeanOrders.tblorderTitle.length];
		for(int i=0;i<orders.size();i++){//放入数据  遍历每一个订单
			for(int j=0;j<BeanOrders.tblorderTitle.length;j++)
			 tblOrderData[i][j]=orders.get(i).getCell(j);
		}
		//建出模型
		tabOrderModel.setDataVector(tblOrderData,tblOrderTitle);//放入数据和表头
		this.dataTableOrder.validate();
		this.dataTableOrder.repaint();
	}

	public FrmOrders() {
		//初始最大化
		this.setExtendedState(Frame.MAXIMIZED_BOTH);//public int getExtendedState()获取此窗体的状态。该状态表示为逐位掩码。 
		this.setTitle("我的订单");
		
		//菜单
		this.menubar.add(menuItem_show);this.menuItem_show.addActionListener(this);
		this.menubar.add(menuItem_delete);this.menuItem_delete.addActionListener(this);
		this.menubar.add(menuItem_comment);this.menuItem_comment.addActionListener(this);
		this.menubar.add(menuItem_return);this.menuItem_return.addActionListener(this);
		this.setJMenuBar(menubar);
		
		this.getContentPane().add(new JScrollPane(this.dataTableOrder), BorderLayout.CENTER);
		
		try {
			this.reloadOrderTable();
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.menuItem_show){
			
			
		}
		else if(e.getSource()==this.menuItem_comment){
		
			
			int i=FrmOrders.this.dataTableOrder.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "请选择要评价的订单", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			FrmComment dlg=new FrmComment(this,"评价订单",true);
			dlg.setVisible(true);
			
			if(FrmComment.comment.size()!=0){//评价填写
				String goods_name=null;
				String goods_id=null;
				String shop_id=null;
				String customer_id=null;
				Connection conn=null;
				try {
					String sql="select good_name,goods_id from goods_msg where goods_id=("
							+ "select goods_id from order_detil "
							+ "where order_id=?"
							+ ")";
					conn=DBUtil.getConnection();
					java.sql.PreparedStatement pst=conn.prepareStatement(sql);
					
					pst.setString(1, FrmOrders.orders.get(i).getOrder_id());
					java.sql.ResultSet rs=pst.executeQuery();
					if(rs.next()){
						goods_name=rs.getString(1);
						goods_id=rs.getString(2);
					}
					
					sql="select shop_id,customer_id from goods_order"
							+ " where order_id=?";
					pst=conn.prepareStatement(sql);
					pst.setString(1, FrmOrders.orders.get(i).getOrder_id());
					rs=pst.executeQuery();
					if(rs.next()){
						shop_id=rs.getString(1);
						customer_id=rs.getString(2);
					}
					
					rs.close();
					sql="insert into goods_comment(goods_id,shop_id,customer_id,comment_content,comment_date,star)"
							+ "VALUES(?,?,?,?,?,?)";//?占位符
					
					pst=conn.prepareStatement(sql);
					pst.setString(1, goods_id);
					pst.setString(2, shop_id);
					pst.setString(3, customer_id);
					pst.setString(4, FrmComment.comment.get(0));
					Timestamp time = Timestamp.valueOf(FrmComment.comment.get(3));
					pst.setTimestamp(5, time);
					pst.setString(6, FrmComment.comment.get(1));
					pst.execute();
					
					sql="update shop_msg"
							+ " set shop_star=(shop_star*all_comment_count+4.0)/(all_comment_count+1),all_comment_count=all_comment_count+1"
							+ " WHERE shop_id=?";
					pst=conn.prepareStatement(sql);
					pst.setString(1, shop_id);
					pst.execute();
					
					//更改骑手信息
//					sql="update shop_msg"
//							+ " set shop_star=(shop_star*all_comment_count+4.0)/(all_comment_count+1),all_comment_count=all_comment_count+1"
//							+ " WHERE shop_id=?";
//					pst=conn.prepareStatement(sql);
//					pst.setString(1, shop_id);
//					pst.execute();
					
					rs.close();
					pst.close();
					
				} 
				catch(SQLException ex){
					ex.printStackTrace();
					try {
						throw new DbException(ex);
					} catch (DbException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}finally{
					if(conn!=null){
						try{
							conn.close();
						}catch(SQLException ex){
							ex.printStackTrace();
						}
					}
				}
				FrmComment.comment.clear();
			}
			
		}
		else if(e.getSource()==this.menuItem_delete){
			int i=FrmOrders.this.dataTableOrder.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null, "请选择要删除的订单", "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				util.ordersManager.deleteOrders(this.orders.get(i));
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==this.menuItem_return){
			this.setVisible(false);
			
		}
	}

}
