package com.happyweekend.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class Login {
	@Getter@Setter
	private Integer id;
	@Getter@Setter
    private Integer personId;
	@Getter@Setter
	private String loginName;
	@Getter@Setter
	private String password;
	
	

}
