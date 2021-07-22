package yb.dragon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import yb.dragon.common.Login;
import yb.dragon.service.LoginService;

@RestController
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	public Login queryByNum(String numer) {
		return loginService.queryByNum(numer);
	}
}
