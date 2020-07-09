package take_away_assistant.bean;

public class BeanClass {
	public static final String[] tblClassTitle={"商品分类"};
	//col表示界面表格中的列序号，0开始

	private String class_ID;
	private String class_name;
	private int oneclass_good_count;
	public String getCell(int col){
		if(col==0) return this.class_name;
		else return "";
	}
	public void set_classID(String class_ID){
		this.class_ID=class_ID;
	}
	public String get_classID(){
		return class_ID;
	}
	public void set_className(String class_name){
		this.class_name=class_name;
	}
	public String get_className(){
		return class_name;
	}
	
	public void set_setOneclass_good_count(int class_name){
		this.oneclass_good_count=class_name;
	}
	public int get_getOneclass_good_count(){
		return oneclass_good_count;
	}
}
