package com.happyweekend.spring.form;

import com.happyweekend.models.PersonType;
import com.happyweekend.spring.form.validator.annotation.Birthday;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

//@EqualPassword(pass1 = "password",pass2 = "rePassword")
public class PersonForm {

    @NotNull
    private int id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Email
    private String email;
    @Getter@Setter
    @NotNull
    private int idLogin;

    private String username;
    @NotBlank
    @Size(min = 8,max = 20)
    private String password;
    @NotBlank
    @Size(min = 8,max = 20)
    private String rePassword;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Birthday
    private LocalDate birthday;

    @Getter@Setter
    private List<PersonType> personTypeList;
    @Getter@Setter
    private int personTypeId;

    @Getter@Setter
    private String photo;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
