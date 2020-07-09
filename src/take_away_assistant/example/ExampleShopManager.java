package take_away_assistant.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import take_away_assistant.Itf.IShopManager;
import take_away_assistant.bean.BeanShop;
import take_away_assistant.bean.BeanUser;
import take_away_assistant.others.BaseException;
import take_away_assistant.others.DBUtil;
import take_away_assistant.others.DbException;

public class ExampleShopManager implements IShopManager {

	@Override
	public BeanShop addShop(String name) throws BaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BeanShop> loadAll() throws BaseException {
		List<BeanShop> result=new ArrayList<BeanShop>();
		Connection conn=null;
		try{
			conn=DBUtil.getConnection();
			String sql="select shop_id,shop_name,shop_star,consume_aver,sales_volume from shop_msg";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				BeanShop p=new BeanShop();
				p.setShop_id(rs.getString(1));
				p.setShop_name(rs.getString(2));
				p.setShop_star(rs.getBigDecimal(3));
				p.setShop_consume_aver(rs.getInt(4));
				p.setShop_sales_volume(rs.getInt(5));
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
	public void deletePlan(BeanShop plan) throws BaseException {
		// TODO Auto-generated method stub
		
	}

}
