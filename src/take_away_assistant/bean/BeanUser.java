package take_away_assistant.bean;

import java.sql.Timestamp;


public class BeanUser {
	public static BeanUser currentLoginUser=null;//全局变量当前用户
	public static int currentLoginUserType=0;//0客户  1管理员
	private String user_id;

	private Timestamp register_time;
	private String customer_name;
	private String customer_sex;
	private String user_pwd;
	private String customer_tel;
	private String customer_mail;
	private String customer_city;
	private Timestamp customer_register_date;
	private String vip;
	private Timestamp vip_deadline;
	public void setUser_id(String userid) {

		this.user_id=userid;
	}
	public String getUser_id(){
		return user_id;
	}
	public void setRegister_time(Timestamp timestamps) {
		this.register_time=timestamps;
	}
	public Timestamp getRegister_time(){
		return register_time;
	}

	public void setPwd(String newPwd) {
		this.user_pwd=newPwd;
	}
	public String getPwd(){
		return user_pwd;
	}
	
	public void setcustomer_name(String newPwd) {
		this.customer_name=newPwd;
	}
	public String getcustomer_name(){
		return customer_name;
	}
	public void setcustomer_sex(String customer_sex) {
		this.customer_sex=customer_sex;
	}
	public String getcustomer_sex(){
		return customer_sex;
	}
	
	
	public void setcustomer_tel(String customer_tel) {
		this.customer_tel=customer_tel;
	}
	public String getcustomer_tel(){
		return customer_tel;
	}
	
	public void setcustomer_mail(String customer_mail) {
		this.customer_mail=customer_mail;
	}
	public String getcustomer_mail(){
		return customer_mail;
	}
	public void setcustomer_city(String customer_city) {
		this.customer_city=customer_city;
	}
	public String getcustomer_city(){
		return customer_city;
	}
	
	public void setcustomer_register_date(Timestamp vip) {
		this.customer_register_date=vip;
	}
	public Timestamp getcustomer_register_date(){
		return customer_register_date;
	}


	
	public void setVip(String vip) {
		this.vip=vip;
	}
	public String getVip(){
		return vip;
	}

	
	public void setvip_deadline(Timestamp vip) {
		this.vip_deadline=vip;
	}
	public Timestamp getvip_deadline(){
		return vip_deadline;
	}
}
