package com.idbms.shoppingmall.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.idbms.shoppingmall.model.Shops;


@Repository("shopsRepository")
public interface ShopRepository extends JpaRepository<Shops, Integer>{

	Shops findByShopID(int shopID);
	
	Shops findByShopNumber(String shopNumber);
	
}
