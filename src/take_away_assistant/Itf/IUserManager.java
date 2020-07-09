package take_away_assistant.Itf;

import take_away_assistant.bean.BeanUser;
import take_away_assistant.others.BaseException;


public interface IUserManager {
	public BeanUser reg(String userid,String tel, String pwd,String pwd2) throws BaseException;
	public BeanUser login(String userid,String pwd)throws BaseException;
	public void changePwd(BeanUser user, String oldPwd,String newPwd, String newPwd2)throws BaseException;
	public void comMsg(BeanUser user,String edtName,String edtSex,
			String edtMail,String edtCity)throws BaseException;
	public void sMsg(BeanUser user);
	
}
