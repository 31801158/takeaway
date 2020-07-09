package take_away_assistant.bean;


public class BeanGoods {
	public static final String[] tblGoodsTitle={"商品名称","价格","现在购买的优惠价"};;
	private String goods_id;
	private String goods_name;
	private String goods_class_id;
	private float price;
	private float d_price;
	public String getCell(int col){
		if(col==0) return this.goods_name;
		else if(col==1) return Float.toString(this.price);
		else if(col==2) return Float.toString(this.d_price);
		else return null;
	}
	
	public void setGoodsID(String goodsid){
		this.goods_id=goodsid;
	}
	public String getGoodsID(){
		return goods_id;
	}
	public void setGoodsName(String goodsname){
		this.goods_name=goodsname;
	}
	public String getGoodsName(){
		return goods_name;
	}
	public void setGoodsClassId(String goodsname){
		this.goods_class_id=goodsname;
	}
	public String getGoodsClassId(){
		return goods_class_id;
	}
	

	public void setPrice(float goodsname){
		this.price=goodsname;
	}
	public float getPrice(){
		return price;
	}
		
	public void setDPrice(float goodsname){
		this.d_price=goodsname;
	}
	public float getDPrice(){
		return d_price;
	}
	
}
