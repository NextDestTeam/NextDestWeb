package com.happyweekend.service;

import java.util.ArrayList;
import java.util.List;

import com.happyweekend.models.Login;
import com.happyweekend.service.interfaces.ILoginService;

public class LoginService implements ILoginService{

	private static List<Login> logins = new ArrayList<>();
	
	public LoginService() {
		Login l = new Login("lucas", "p");
		logins.add(l);
	}
	
	@Override
	public boolean validLogin(Login login) {		
		return logins.stream().filter(
				l->l.getLoginName().equals(login.getLoginName())&&
						l.getPassword().equals(login.getPassword())
				).findFirst().isPresent();
		
	}

	@Override
	public void save(Login login) {
		logins.add(login);
		
	}

}
