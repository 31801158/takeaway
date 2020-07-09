package take_away_assistant.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;




import take_away_assistant.Itf.IClassManager;
import take_away_assistant.bean.BeanClass;
import take_away_assistant.bean.BeanShop;
import take_away_assistant.others.BaseException;
import take_away_assistant.others.DBUtil;
import take_away_assistant.others.DbException;

public class ExampleClassManager implements IClassManager {

	@Override
	public List<BeanClass> loadClass(BeanShop shop) throws BaseException {
		
		List<BeanClass> result=new ArrayList<BeanClass>();
		Connection conn=null;
		try{
			conn=DBUtil.getConnection();
			String sql="select goods_class_name,goods_class_id,oneclass_good_count FROM good_class"
					+ " where goods_class_id in"
					+ "(SELECT DISTINCT goods_class_id from goods_msg where goods_id in "
					+ "(SELECT goods_id from relation_shopandgoods WHERE shop_id=?))";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, shop.getShop_id());
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				BeanClass p=new BeanClass();
				p.set_className(rs.getString(1));
				p.set_classID(rs.getString(2));
				p.set_setOneclass_good_count(rs.getInt(3));
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
