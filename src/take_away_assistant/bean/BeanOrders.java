package take_away_assistant.bean;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BeanOrders {
	public static final String[] tblorderTitle={"订单编号","商店编号","骑手编号","原始金额","结算金额","满减编号","使用优惠券编号","下单时间","要求送达时间","配送地址编号","订单状态","评价状态"};
	private String order_id;
	private String shop_id;
	private String customer_id;
	private String rider_id;
	private float original_price;
	private float final_price;
	private String full_redution_id;
	private String ticket_id;
	private Timestamp order_time;
	private Timestamp require_arrive_time;
	private String address_id;
	private String order_condition;
	private String ifcomment;
	public String getCell(int col){
		if(col==0) return this.order_id;
		else if(col==1) return this.shop_id;
		else if(col==2) return this.rider_id;
		else if(col==3) return Float.toString(this.original_price);
		else if(col==4) return Float.toString(this.final_price);
		else if(col==5) return this.full_redution_id;
		else if(col==6) return this.ticket_id;
		else if(col==7){
			String dateStr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(this.order_time);
			return dateStr;
		}
		else if(col==8){
			String dateStr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(this.require_arrive_time);
			return dateStr;
		}
		else if(col==9) return this.address_id;
		else if(col==10) return this.order_condition;
		else if(col==11) return this.ifcomment;
		else return "";
	}
	
	public void setOrder_id(String stepid){
		this.order_id=stepid;
	}
	public String getOrder_id(){
		return order_id;
	}
	
	public void setShop_id(String stepid){
		this.shop_id=stepid;
	}
	public String getShop_id(){
		return shop_id;
	}
	
	public void setCustomer_id(String stepid){
		this.customer_id=stepid;
	}
	public String getCustomer_id(){
		return customer_id;
	}
	
	public void setRider_id(String stepid){
		this.rider_id=stepid;
	}
	public String getRider_id(){
		return rider_id;
	}
	
	public void setOriginal_price(float stepid){
		this.original_price=stepid;
	}
	public float getOriginal_price(){
		return original_price;
	}
	
	public void setFinal_price(float stepid){
		this.final_price=stepid;
	}
	public float getFinal_price(){
		return final_price;
	}
	
	public void setFull_redution_id(String stepid){
		this.full_redution_id=stepid;
	}
	public String getFull_redution_id(){
		return full_redution_id;
	}
	
	public void setTicket_id(String stepid){
		this.ticket_id=stepid;
	}
	public String getTicket_id(){
		return ticket_id;
	}
	
	public void setOrder_time(Timestamp ordertime){
		this.order_time= ordertime;
	}
	public Timestamp getOrder_time(){
		return order_time;
	}
	
	public void setrequire_arrive_time(Timestamp stepid){
		this.require_arrive_time=stepid;
	}
	public Timestamp getrequire_arrive_time(){
		return require_arrive_time;
	}

	
	public void setAddress_id(String stepid){
		this.address_id=stepid;
	}
	public String getAddress_id(){
		return address_id;
	}
	
	public void setOrder_condition(String stepid){
		this.order_condition=stepid;
	}
	public String getOrder_condition(){
		return order_condition;
	}
	
	public void setIfcomment(String stepid){
		this.ifcomment=stepid;
	}
	public String getIfcomment(){
		return ifcomment;
	}

	
}
