package com.idbms.shoppingmall.service;

import java.util.List;
import java.util.Set;

import com.idbms.shoppingmall.model.Bid;
import com.idbms.shoppingmall.model.Role;
import com.idbms.shoppingmall.model.User;

public interface UserService {
	public User findUserByEmail(String email,int role);
	public void saveUser(User user,String role);
	public Role findByRole(String role);
	public Set<Bid> findAllBids();
}
