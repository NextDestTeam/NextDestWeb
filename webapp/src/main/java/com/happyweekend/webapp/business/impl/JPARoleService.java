package com.happyweekend.webapp.business.impl;

import org.springframework.stereotype.Service;

import com.happyweekend.webapp.business.RoleService;
import com.happyweekend.webapp.business.model.Role;

@Service
public class JPARoleService extends JPACRUDService<Long, Role> implements RoleService {

}
