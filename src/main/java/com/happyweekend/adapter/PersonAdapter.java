package com.happyweekend.adapter;

import com.happyweekend.models.Login;
import com.happyweekend.models.Person;
import com.happyweekend.models.PersonType;
import com.happyweekend.spring.form.PersonForm;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class PersonAdapter {

    public Person toPerson(PersonForm form){

        Person person = new Person();

        person.setId(form.getId());
        person.setLogin(new Login(form.getIdLogin(),form.getId(),form.getUsername(),form.getPassword()));
        person.setPersonTypeId(form.getPersonTypeId());
        person.setEmail(form.getEmail());
        person.setFirstName(form.getFirstName());
        person.setLastName(form.getLastName());
        person.setAge(Date.from(form.getBirthday().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        return person;

    }

    public PersonForm toPersonForm(Person person, Login login, List<PersonType> personTypeList) {
        PersonForm personForm = new PersonForm();

        personForm.setIdLogin(login.getId());
        personForm.setUsername(login.getLoginName());
        personForm.setPassword(login.getPassword());

        personForm.setId(person.getId());
        personForm.setEmail(person.getEmail());
        if(person.getAge()!=null)
            personForm.setBirthday(((Date)person.getAge()).toLocalDate());
        personForm.setFirstName(person.getFirstName());
        personForm.setLastName(person.getLastName());

        personForm.setPersonTypeList(personTypeList);

        return personForm;
    }
}
