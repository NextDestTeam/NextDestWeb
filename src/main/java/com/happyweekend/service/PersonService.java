package com.happyweekend.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.happyweekend.connection.ConnectionManager;
import com.happyweekend.dao.ImageDao;
import com.happyweekend.dao.PersonDao;
import com.happyweekend.enumarator.PersonTypeEnum;
import com.happyweekend.models.Image;
import com.happyweekend.models.Person;
import com.happyweekend.models.PersonType;
import com.happyweekend.service.interfaces.ILoginService;
import com.happyweekend.service.interfaces.IPersonService;
import com.happyweekend.service.interfaces.IPersonTypeService;

public class PersonService implements IPersonService{
	
		@Override
	public List<PersonType> getPersonTypes() {

			IPersonTypeService personTypeService = new PersonTypeService();
			return personTypeService.getAll();
	}

	@Override
	public Person get(int id) {
		Connection con = ConnectionManager.getInstance().connect();
		PersonDao dao = new PersonDao(con);
		Person p = dao.get(id);
		if(p.getImageId()>0 && p.getImageId()!=null){
			ImageDao imageDao = new ImageDao(con);
			Image image = imageDao.get(p.getImageId());
			p.setImage(image);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}


	public void save(Person person) {
		Connection con = ConnectionManager.getInstance().connect();
		if(person.getImage()!=null){
			ImageService imageService = new ImageService();
			imageService.save(person.getImage());
			person.setImageId(person.getImage().getId());
		}
		PersonDao dao = new PersonDao(con);
		person.setPersonTypeId(PersonTypeEnum.EVENT_MANAGER.getValue());
		if(person.getId()==null)
			dao.save(person);
		else
			dao.update(person);
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void save(Person person, ILoginService service) {
		Connection con = ConnectionManager.getInstance().connect();
		PersonDao dao = new PersonDao(con);
		person.setPersonTypeId(PersonTypeEnum.EVENT_MANAGER.getValue());
		if(person.getImage()!=null){
			ImageService imageService = new ImageService();
			imageService.save(person.getImage());
			person.setImageId(person.getImage().getId());
		}
		if(person.getId()==null)
			dao.save(person);
		else
			dao.update(person);
		person.getLogin().setPersonId(person.getId());

		service.save(person.getLogin());
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


}
