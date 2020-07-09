package take_away_assistant.Itf;

import java.util.List;

import take_away_assistant.bean.BeanClass;
import take_away_assistant.bean.BeanShop;
import take_away_assistant.others.BaseException;


public interface IClassManager {
	public List<BeanClass> loadClass(BeanShop shop)throws BaseException;
	//public void add(BeanShop shop, String name)throws BaseException;
	//public void deleteStep(BeanClass step)throws BaseException;
}
