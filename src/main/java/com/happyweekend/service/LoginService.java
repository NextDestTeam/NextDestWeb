package com.happyweekend.service;

import java.sql.Connection;
import java.sql.SQLException;
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

		Connection con = ConnectionManager.getInstance().connect();
		encryptPassword(login);
		LoginDao dao = new LoginDao(con);

        Login l = dao.get(login);
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (l.getId()!=null && l!=null);
	}



	@Override
	public void save(Login login) {
		Connection con = ConnectionManager.getInstance().connect();
		encryptPassword(login);
		LoginDao dao = new LoginDao(con);

		if(login.getId()==null)
			dao.save(login);
		else
			dao.update(login);
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Login loadByUsername(String username) {
		Connection con = ConnectionManager.getInstance().connect();
		LoginDao dao = new LoginDao(con);

		Login l = null;
		try {
			l = dao.loadByUsername(username);
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l;


	}

	private void encryptPassword(Login login) {
		login.setPassword(new String(
				DigestUtils.md5DigestAsHex(login.getPassword().getBytes()).toUpperCase()
		));
	}

	public String getEncryptedPassword(String password){
		return new String(
				DigestUtils.md5DigestAsHex(password.getBytes()).toUpperCase()
		);
	}

}
