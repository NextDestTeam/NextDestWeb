package com.happyweekend.spring.controllers;

import com.happyweekend.models.Login;
import com.happyweekend.models.Person;
import com.happyweekend.service.ImageService;
import com.happyweekend.service.LoginService;
import com.happyweekend.service.PersonService;
import com.happyweekend.service.interfaces.ILoginService;
import com.happyweekend.spring.form.RegisterForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class RegisterController {


	private PersonService service = new PersonService();
	private ImageService imageService = new ImageService();
	private ILoginService loginService = new LoginService();

	/*@GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }*/


	@GetMapping("/register")
	public ModelAndView index() {

		return new ModelAndView("register1.html","registerForm",new RegisterForm());
	}
	
	@PostMapping("/register")
	public ModelAndView register(@ModelAttribute("registerForm") @Valid RegisterForm form, BindingResult result) {

		if(loginService.loadByUsername(form.getUsername())!=null){
			result.addError(new FieldError("registerForm","username","Username already in use."));
		}

		if(result.hasErrors()){
			return new ModelAndView("register1.html","registerForm",form);
		}

		service.save(registerFormToPerson(form),loginService);


		return new ModelAndView("redirect:/login.html");

		
	}

	private Person registerFormToPerson(RegisterForm registerForm){
		Person p = new Person();
		Login l = new Login();

		p.setFirstName(registerForm.getFirstName());
		p.setLastName(registerForm.getLastName());
		p.setEmail(registerForm.getEmail());
		l.setLoginName(registerForm.getUsername());
		l.setPassword(registerForm.getPassword());
		p.setImage(imageService.getDefaultProfileImage());
		p.setLogin(l);
		return p;

	}
	

}
