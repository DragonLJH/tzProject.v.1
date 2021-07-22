package yb.dragon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yb.dragon.common.Login;
import yb.dragon.dao.LoginDao;

@Service
public class LoginService {

	@Autowired
	private LoginDao loginDao;
	
	
	public Login queryByNum(String numer) {
		return loginDao.queryByNum(numer);
	}
	
}
