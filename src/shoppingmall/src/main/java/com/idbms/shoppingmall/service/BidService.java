package com.idbms.shoppingmall.service;

import javax.validation.Valid;

import com.idbms.shoppingmall.model.Bid;
import com.idbms.shoppingmall.model.Role;
import com.idbms.shoppingmall.model.User;

public interface BidService {

	public void addBid(Bid bid,User user);
	
	public void deleteBid(Integer bidID,int userId);
	
	public Bid findByBidID(Integer bidID);

	public void saveBid(Bid bid, User user);
}
