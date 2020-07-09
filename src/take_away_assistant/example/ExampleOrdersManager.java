package take_away_assistant.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import take_away_assistant.Itf.IOrdersManager;
import take_away_assistant.bean.BeanOrders;
import take_away_assistant.bean.BeanUser;
import take_away_assistant.others.BaseException;
import take_away_assistant.others.BusinessException;
import take_away_assistant.others.DBUtil;
import take_away_assistant.others.DbException;
import take_away_assistant.ui.FrmOrders;

public class ExampleOrdersManager implements IOrdersManager {

	@Override
	public List<BeanOrders> loadOrders() throws BaseException {
		List<BeanOrders> result=new ArrayList<BeanOrders>();
		Connection conn=null;
		try{
			conn=DBUtil.getConnection();
			String sql="select order_id,shop_id,customer_id,rider_id,"
					+ "original_price,final_price,full_redution_id,ticket_id,"
					+ "order_time,require_arrive_time,address_id,order_condition,ifcomment"
					+ " from goods_order where customer_id=? order by order_id";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, BeanUser.currentLoginUser.getUser_id());
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				
				BeanOrders p=new BeanOrders();
				p.setOrder_id(rs.getString(1));
				p.setShop_id(rs.getString(2));
				p.setCustomer_id(rs.getString(3));
				p.setRider_id(rs.getString(4));
				p.setOriginal_price(rs.getFloat(5));
				p.setFinal_price(rs.getFloat(6));
				p.setFull_redution_id(rs.getString(7));
				p.setTicket_id(rs.getString(8));
				p.setOrder_time(rs.getTimestamp(9));
				p.setrequire_arrive_time(rs.getTimestamp(10));
				p.setAddress_id(rs.getString(11));
				p.setOrder_condition(rs.getString(12));
				p.setIfcomment(rs.getString(13));
				result.add(p);
			}
			rs.close();
			pst.close();
			conn.close();
		
		}
		catch(SQLException ex){
			throw new DbException(ex);
		}
		finally{
			if(conn!=null){
				try{
					conn.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	@Override
	public void deleteOrders(BeanOrders orders) throws BaseException {
		String order_id=orders.getOrder_id();
		Connection conn=null;
		try{
			conn=DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql="select order_condition from goods_order where order_id="+order_id;//查看是否可以删除，若存在步骤则不可以删
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			if(rs.next()){
				if(rs.getString(1).equals("配送中")||rs.getString(1).equals("超时")){
					rs.close();
					st.close();
					throw new BusinessException("订单未完成，不可以删除！");
				}
			}
			rs.close();
			
			//不允许删别人的
			sql="select customer_id from goods_order where order_id="+order_id;
			rs=st.executeQuery(sql);
			String customer_id=null;
			if(rs.next()){
				customer_id=rs.getString(1);
			}
			else {
				rs.close();
				st.close();
				throw new BusinessException("该订单不存在");
			}
			rs.close();
			if(!BeanUser.currentLoginUser.getUser_id().equals(customer_id)){
				st.close();
				throw new BusinessException("不能删除别人的订单");
			}
			
			sql="delete from goods_order where order_id="+order_id;
			st.execute(sql);
			sql="delete from order_detil where order_id="+order_id;
			st.execute(sql);
			st.close();
			
			
			conn.commit();
		}catch(BaseException e){
			try {
				conn.rollback();
			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			throw e;
			
		}catch(SQLException ex){
			ex.printStackTrace();
			try{
				conn.rollback();
			}catch(SQLException e){
				e.printStackTrace();
			}
			throw new DbException(ex);
		}
		finally{
			if(conn!=null){
				try{
					conn.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
		JOptionPane.showMessageDialog(null, "删除成功！", "已删除",JOptionPane.INFORMATION_MESSAGE);
		
	}

	@Override
	public void showAllOrders() throws BaseException {
		loadOrders();
	}
	
}
