package take_away_assistant.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import take_away_assistant.Itf.IGoodsManager;
import take_away_assistant.bean.BeanClass;
import take_away_assistant.bean.BeanGoods;
import take_away_assistant.others.BaseException;
import take_away_assistant.others.DBUtil;
import take_away_assistant.others.DbException;

public class ExampleGoodsManager implements IGoodsManager {

	@Override
	public List<BeanGoods> loadGoods(BeanClass aclass) throws BaseException {
		List<BeanGoods> result=new ArrayList<BeanGoods>();
		Connection conn=null;
		try{
			conn=DBUtil.getConnection();
			String sql="select DISTINCT goods_id,good_name,goods_class_id,price,d_price"
					+ " from goods_msg where goods_id in"
					+ " (select t1.goods_id from goods_msg t1,relation_shopandgoods t2"
					+ " where t1.goods_id=t2.goods_id and goods_class_id=?)";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, aclass.get_classID());
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				BeanGoods p=new BeanGoods();
				p.setGoodsName(rs.getString(2));
				p.setGoodsID(rs.getString(1));
				p.setGoodsClassId(rs.getString(3));
				p.setPrice(rs.getFloat(4));
				p.setDPrice(rs.getFloat(5));
				
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

}
