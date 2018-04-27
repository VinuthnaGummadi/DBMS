package com.idbms.shoppingmall.service;

import com.idbms.shoppingmall.model.Role;
import com.idbms.shoppingmall.model.User;

public interface UserService {
	public User findUserByEmail(String email,int role);
	public void saveUser(User user,String role);
	public Role findByRole(String role);
}
