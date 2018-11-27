package com.happyweekend.service;

import java.util.ArrayList;
import java.util.List;

import com.happyweekend.connection.ConnectionManager;
import com.happyweekend.dao.LoginDao;
import com.happyweekend.models.Login;
import com.happyweekend.service.interfaces.ILoginService;
import org.springframework.context.annotation.Bean;
import org.springframework.util.DigestUtils;


public class LoginService implements ILoginService{

	private static List<Login> logins = new ArrayList<>();
	
	public LoginService() {
		Login l = new Login();
		
		l.setLoginName("lucas");
		//byte[] encodedPassword =  MessageDigest.getInstance("MD5").digest("p".getBytes());
		l.setPassword("p");
		logins.add(l);
	}
	
	@Override
	public boolean validLogin(Login login) {

		encryptPassword(login);
		LoginDao dao = new LoginDao(ConnectionManager.getInstance().connect());

        Login l = dao.get(login);
		return (l!=null && l.getId()>0);
	}



	@Override
	public void save(Login login) {
		encryptPassword(login);
		LoginDao dao = new LoginDao(ConnectionManager.getInstance().connect());

		dao.save(login);
		
	}

	private void encryptPassword(Login login) {
		login.setPassword(new String(
				DigestUtils.md5DigestAsHex(login.getPassword().getBytes()).toUpperCase()
		));
	}

}
