package com.happyweekend.spring.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		System.out.println("configure ------------------------------------------------------------");
		http.authorizeRequests()
		.anyRequest().permitAll();/*.and().authenticated()
		.and()
		.formLogin()
		.loginPage("/login")
		.defaultSuccessUrl("/register")
		.permitAll()
		.and()
		.httpBasic();*/
	}

}
