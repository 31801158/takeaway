package take_away_assistant.Itf;

import java.util.List;

import take_away_assistant.bean.BeanShop;
import take_away_assistant.others.BaseException;


public interface IShopManager {
	public BeanShop addShop(String name) throws BaseException;//����Ա
	
	public List<BeanShop> loadAll()throws BaseException;//�˿� �˿�
	
	public void deletePlan(BeanShop plan)throws BaseException;//����Ա
}
