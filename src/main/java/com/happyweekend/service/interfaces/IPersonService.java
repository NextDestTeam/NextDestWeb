package com.happyweekend.service.interfaces;

import java.util.List;

import com.happyweekend.models.Person;
import com.happyweekend.models.PersonType;

public interface IPersonService {

	public List<PersonType> getPersonTypes();

	public  Person get(int id);

    void save(Person person,ILoginService iLoginService);
}
