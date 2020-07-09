package take_away_assistant;


import take_away_assistant.Itf.IClassManager;
import take_away_assistant.Itf.IGoodsManager;
import take_away_assistant.Itf.IOrdersManager;
import take_away_assistant.Itf.IShopManager;
import take_away_assistant.Itf.IUserManager;
import take_away_assistant.example.ExampleClassManager;
import take_away_assistant.example.ExampleGoodsManager;
import take_away_assistant.example.ExampleOrdersManager;
import take_away_assistant.example.ExampleShopManager;
import take_away_assistant.example.ExampleUserManager;

public class util {

	public static IShopManager shopManager=new ExampleShopManager();//需要换成自行设计的实现类
	public static IClassManager classManager=new ExampleClassManager();//需要换成自行设计的实现类
	public static IUserManager userManager=new ExampleUserManager();//需要换成自行设计的实现类
	public static IGoodsManager goodsManager=new ExampleGoodsManager();
	public static IOrdersManager ordersManager=new ExampleOrdersManager();
}
