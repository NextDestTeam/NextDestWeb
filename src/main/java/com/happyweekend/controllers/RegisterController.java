package com.happyweekend.controllers;

import java.util.Arrays;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.happyweekend.models.Person;

@Controller
public class RegisterController {
	
	/*@GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }*/
	@GetMapping("/register")
	public ModelAndView index() {		
		//return "register.form";
		String message = "<br><div style='text-align:center;'>"
				+ "<h3>********** Hello World, Spring MVC Tutorial</h3>This message is coming from CrunchifyHelloWorld.java **********</div><br><br>";
		//return new ModelAndView("welcome", "message", message);
		Person p1 = new Person();
		p1.setFirstName("lucas");
		return new ModelAndView("jspPersona","persons",Arrays.asList(p1));
	}
	
	@PostMapping("register")
	public String register() {
		return "index";
		
	}
	

}
