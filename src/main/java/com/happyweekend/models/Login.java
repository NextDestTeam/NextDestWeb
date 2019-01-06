package com.happyweekend.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class Login implements Serializable {
	@Getter@Setter
	private Integer id;
	@Getter@Setter
    private Integer personId;
	@Getter@Setter
	private String loginName;
	@Getter@Setter
	private String password;
	
	

}
