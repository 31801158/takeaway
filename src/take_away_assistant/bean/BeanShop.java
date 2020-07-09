package take_away_assistant.bean;

import java.math.BigDecimal;
import take_away_assistant.others.BusinessException;
import take_away_assistant.others.DbException;


public class BeanShop {
	public static final String[] tableTitles={"商家编号","商家名称","星级","人均消费","总销量"};
	private String shop_id;
	private String shop_name;
	private BigDecimal shop_star;
	private int consume_aver;
	private int sales_volume;
	private int all_comment_count;
	
	public String getCell(int col) throws DbException, BusinessException{
		String a=new BigDecimal(this.shop_star.toString()).toString();
			if(col==0) return this.shop_id;
			else if(col==1) return this.shop_name;
			else if(col==2) return  a;
			else if(col==3) return Integer.toString(this.consume_aver);
			else if(col==4) return Integer.toString(this.sales_volume);
			else return "";
		
		
	}
	public void setShop_id(String shopid){
		this.shop_id=shopid;
	}
	public String getShop_id(){
		return shop_id;
	}
	public void setShop_name(String shopname){
		this.shop_name=shopname;
	}
	public String getShop_name(){
		return shop_name;
	}
	
	public void setShop_star(BigDecimal shopname){
		this.shop_star=shopname;
	}
	public BigDecimal getShop_star(){
		return shop_star;
	}
	
	public void setShop_consume_aver(int shopname){
		this.consume_aver=shopname;
	}
	public int getShop_consume_aver(){
		return consume_aver;
	}
	public void setShop_sales_volume(int shopname){
		this.sales_volume=shopname;
	}
	public int getShop_sales_volume(){
		return sales_volume;
	}
	
	public void setAll_comment_count(int all_comment_count){
		this.all_comment_count=all_comment_count;
	}
	public int getAll_comment_count(){
		return all_comment_count;
	}
	
}
