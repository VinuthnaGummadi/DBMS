package com.idbms.shoppingmall.service;

import com.idbms.shoppingmall.model.Floors;
import com.idbms.shoppingmall.model.Mall;
import com.idbms.shoppingmall.model.Shops;

public interface MallService {

	public Mall findByMallID(int mallID);
	
	public void saveFloor(Floors floor);
	
	public void saveShop(Shops shop);
	
	public Shops findByShopNumber(String shopNumber);
	
	public Mall findMallForShopOwner(String email, int roleID);
}
