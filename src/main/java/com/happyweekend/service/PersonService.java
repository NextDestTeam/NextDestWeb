package com.happyweekend.service;

import java.util.ArrayList;
import java.util.List;

import com.happyweekend.connection.ConnectionManager;
import com.happyweekend.dao.PersonDao;
import com.happyweekend.enumarator.PersonTypeEnum;
import com.happyweekend.models.Person;
import com.happyweekend.models.PersonType;
import com.happyweekend.service.interfaces.ILoginService;
import com.happyweekend.service.interfaces.IPersonService;

public class PersonService implements IPersonService{
	
	private static List<Person> persons = new ArrayList<>();

	@Override
	public List<PersonType> getPersonTypes() {
		List<PersonType> personTypes = new ArrayList<>();
		PersonType p1 = new PersonType();
		PersonType p2 = new PersonType();
		personTypes.add(p1);
		personTypes.add(p2);
		return personTypes;
	}

	@Override
	public Person get(int id) {
		PersonDao dao = new PersonDao(ConnectionManager.getInstance().connect());
		return dao.get(id);
	}

	@Override
	public void save(Person person, ILoginService service) {
		PersonDao dao = new PersonDao(ConnectionManager.getInstance().connect());
		person.setPersonTypeId(PersonTypeEnum.EVENT_MANAGER.getValue());
		dao.save(person);
		person.getLogin().setId(person.getId());
		service.save(person.getLogin());

	}


}
