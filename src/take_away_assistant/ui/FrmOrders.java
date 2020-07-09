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
	private JMenuItem  menuItem_show=new JMenuItem("��ʾ���ж���");
    private JMenuItem  menuItem_delete=new JMenuItem("ɾ������");
    private JMenuItem  menuItem_comment=new JMenuItem("ȥ���� > ");
    private JMenuItem  menuItem_return=new JMenuItem("����������");
	private JPanel statusBar = new JPanel();
	
	private Object tblOrderTitle[]=BeanOrders.tblorderTitle;//��ͷ
	private Object tblOrderData[][];//���̼ң�������Ϣ
	DefaultTableModel tabOrderModel=new DefaultTableModel();//DefaultTableModel��ָĬ�ϵı����ģ�� ������������ JTBALE ��JTABLE��GETTABLEMODEL���õ�һ�������ģ�������� JTBALE
	private JTable dataTableOrder=new JTable(tabOrderModel);//TableModel��JTable��ģ���൱������һ��洢���ݶ���TableModelȥ�洢.�����,�ŵ�JTable��ȥ
	

	
	private BeanOrders curOrder=null;
	public static List<BeanOrders> orders=null;//���ж���  һ���б�
//	List<BeanClass> shopClass=null;
	
	private void reloadOrderTable() throws DbException, BusinessException{
		try {
			orders=util.ordersManager.loadOrders();//�г����ж��� ��һ���б�
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		//�������
		tblOrderData =  new Object[orders.size()][BeanOrders.tblorderTitle.length];
		for(int i=0;i<orders.size();i++){//��������  ����ÿһ������
			for(int j=0;j<BeanOrders.tblorderTitle.length;j++)
			 tblOrderData[i][j]=orders.get(i).getCell(j);
		}
		//����ģ��
		tabOrderModel.setDataVector(tblOrderData,tblOrderTitle);//�������ݺͱ�ͷ
		this.dataTableOrder.validate();
		this.dataTableOrder.repaint();
	}

	public FrmOrders() {
		//��ʼ���
		this.setExtendedState(Frame.MAXIMIZED_BOTH);//public int getExtendedState()��ȡ�˴����״̬����״̬��ʾΪ��λ���롣 
		this.setTitle("�ҵĶ���");
		
		//�˵�
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
				JOptionPane.showMessageDialog(null, "��ѡ��Ҫ���۵Ķ���", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			FrmComment dlg=new FrmComment(this,"���۶���",true);
			dlg.setVisible(true);
			
			if(FrmComment.comment.size()!=0){//������д
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
							+ "VALUES(?,?,?,?,?,?)";//?ռλ��
					
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
					
					//����������Ϣ
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
				JOptionPane.showMessageDialog(null, "��ѡ��Ҫɾ���Ķ���", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				util.ordersManager.deleteOrders(this.orders.get(i));
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==this.menuItem_return){
			this.setVisible(false);
			
		}
	}

}
