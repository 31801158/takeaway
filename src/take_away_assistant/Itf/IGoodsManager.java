package take_away_assistant.Itf;

import java.util.List;

import take_away_assistant.bean.BeanClass;
import take_away_assistant.bean.BeanGoods;
import take_away_assistant.others.BaseException;


public interface IGoodsManager {
	public List<BeanGoods> loadGoods(BeanClass aclass)throws BaseException;
}
