package com.happyweekend.service.interfaces;

import com.happyweekend.models.Login;

public interface ILoginService {
	
	public boolean validLogin(Login login);
	public void save(Login login);


    Login loadByUsername(String username);
}
