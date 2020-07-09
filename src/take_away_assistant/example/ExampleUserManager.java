package take_away_assistant.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import take_away_assistant.Itf.IUserManager;
import take_away_assistant.bean.BeanUser;
import take_away_assistant.others.BaseException;
import take_away_assistant.others.BusinessException;
import take_away_assistant.others.DBUtil;
import take_away_assistant.others.DbException;


public class ExampleUserManager implements IUserManager {

	
	
	public BeanUser reg(String userid,String tel, String pwd,String pwd2) throws BaseException {
		if(userid==null || "".equals(userid))throw new BusinessException("�˺Ų���Ϊ��!");
		if(pwd==null || "".equals(pwd))throw new BusinessException("���벻��Ϊ��!");
		if(tel==null || "".equals(tel))throw new BusinessException("�ֻ����벻��Ϊ��!");
		if(!pwd.equals(pwd2)) throw new BusinessException("��������������벻һ�£�");

		Connection conn=null;
		try {
			String sql="select customer_id from customer_msg where customer_id=?";//?ռλ��
			conn=DBUtil.getConnection();
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();//��ѯ
			if(rs.next()){
				
				rs.close();
				pst.close();
				throw new BusinessException("�û��Ѵ���");
			}
			rs.close();
			pst.close();
			sql="insert into customer_msg(customer_id,customer_pwd,customer_register_date,customer_tel) values(?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, userid);
			pst.setString(2, pwd);
			Timestamp t= new java.sql.Timestamp(System.currentTimeMillis());
			pst.setTimestamp(3,t);
			pst.setString(4, tel);
			pst.execute();
			BeanUser user=new BeanUser();
			user.setRegister_time(t);
			user.setUser_id(userid);
			user.setPwd(pwd);
			user.setcustomer_tel(tel);
			rs.close();
			pst.close();
			return user;
			
		} catch (SQLException e) {
			
			throw new DbException(e);
			
		}finally{
			if(conn!=null){
				try{
					conn.close();
				}
				catch(SQLException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public BeanUser login(String userid, String pwd) throws BaseException {
		if(userid==null || "".equals(userid))throw new BusinessException("�˺Ų���Ϊ��!");
		if(pwd==null || "".equals(pwd))throw new BusinessException("���벻��Ϊ��!");
		
		
		
		Connection conn=null;
		try {
			BeanUser user=null;
			conn=DBUtil.getConnection();
			String sql="select customer_id,customer_name,customer_sex,"
					+ "customer_pwd,customer_tel,customer_mail,"
					+ "customer_city,customer_register_date,vip,"
					+ "vip_deadline from customer_msg where customer_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();//��ѯ
			if(rs.next()){//�û�����
				if(rs.getString("customer_pwd").equals(pwd)) {//success	
					
					user=new BeanUser();
					user.setUser_id(userid);
					user.setcustomer_name(rs.getString(2));
					user.setcustomer_sex(rs.getString(3));
					user.setPwd(pwd);
					user.setcustomer_tel(rs.getString(5));
					user.setcustomer_mail(rs.getString(6));
					user.setcustomer_city(rs.getString(7));
					user.setRegister_time(rs.getTimestamp(8));
					user.setVip(rs.getString(9));
					user.setvip_deadline(rs.getTimestamp(10));
					rs.close();
					pst.close();
					return user;
				}
				else{
					throw new BusinessException("�û��������벻��ȷ��");
				}
				
			}
			else throw new BusinessException("�û��������ڣ�");
			
			
			
		} catch (SQLException e) {
			
			throw new DbException(e);
			
		}finally{
			if(conn!=null){
				try{
					conn.close();
				}
				catch(SQLException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void changePwd(BeanUser user, String oldPwd, String newPwd,
			String newPwd2) throws BaseException {

		if(oldPwd==null || "".equals(oldPwd))throw new BusinessException("ԭ���벻��Ϊ��!");
		if(newPwd==null || "".equals(newPwd))throw new BusinessException("�����벻��Ϊ��!");
		if(newPwd2==null || "".equals(newPwd2))throw new BusinessException("�����벻��Ϊ��!");
		if(!newPwd.equals(newPwd2)) throw new BusinessException("�������벻һ�£�");
		
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select customer_pwd from customer_msg where customer_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,user.getUser_id());
			java.sql.ResultSet rs=pst.executeQuery();//��ѯ
			if(rs.next()){
				if(!rs.getString("customer_pwd").equals(oldPwd)){
					throw new BusinessException("ԭ���벻��ȷ��");
				}
			
			
			else{
				
				//String sql1="update tbl_user set user_Pwd=?,register_time=? where user_id=?;";
				sql="update customer_msg set customer_Pwd=? where customer_id=?";
				pst=conn.prepareStatement(sql);
				pst.setString(1,newPwd);
				//pst.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
				pst.setString(2, user.getUser_id());
				pst.execute();//�޸�
				//user.setRegister_time(new Date());
				user.setPwd(newPwd2);
				user.setUser_id(user.getUser_id());
				rs.close();
				pst.close();
				
			}
			}
			
			
		} catch (SQLException e) {
			
			throw new DbException(e);
			
		}finally{
			if(conn!=null){
				try{
					conn.close();
				}
				catch(SQLException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	
		
	}

	@Override
	public void comMsg(BeanUser user, String edtName, String edtSex,
			String edtMail, String edtCity) throws BaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sMsg(BeanUser user) {
		// TODO Auto-generated method stub
		
	}

	
	

}
