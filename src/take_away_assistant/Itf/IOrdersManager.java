package take_away_assistant.Itf;

import java.util.List;


import take_away_assistant.bean.BeanOrders;
import take_away_assistant.others.BaseException;

public interface IOrdersManager {
	public List<BeanOrders> loadOrders()throws BaseException;
	public void showAllOrders()throws BaseException;
	public void deleteOrders(BeanOrders orders)throws BaseException;
}
