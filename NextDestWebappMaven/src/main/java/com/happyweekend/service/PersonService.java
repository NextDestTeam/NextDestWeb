package com.happyweekend.service;

import java.util.ArrayList;
import java.util.List;

import com.happyweekend.models.Person;
import com.happyweekend.models.PersonType;
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
	
	

}
