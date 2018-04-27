package com.idbms.shoppingmall.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idbms.shoppingmall.model.Floors;
import com.idbms.shoppingmall.model.Mall;
import com.idbms.shoppingmall.model.Shops;
import com.idbms.shoppingmall.repository.FloorRepository;
import com.idbms.shoppingmall.repository.MallRepository;
import com.idbms.shoppingmall.repository.ShopRepository;

@Service("mallService")
public class MallServiceImpl implements MallService{

	@Autowired
    private MallRepository mallRepository;
	
	@Autowired
	private FloorRepository floorRepository;
	
	@Autowired
	private ShopRepository shopRepository;

	@Override
	public Mall findByMallID(int mallID) {
		
		Mall mall = mallRepository.findByMallId(mallID);

		
		return mall;
	}

	@Override
	public void saveFloor(Floors floor) {
		try {
			Mall mallObj = mallRepository.findByMallId(1);
			List<Floors> floors = mallObj.getFloors();
			if(floors!=null){
				floors.add(floor);
				mallObj.setFloors(floors);
			}
			else{
				List<Floors> newFloor = new ArrayList<Floors>();
				newFloor.add(floor);
				mallObj.setFloors(newFloor);
			}
			
			
			mallRepository.save(mallObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void saveShop(Shops shop) {
		try {
			Floors floorObj = floorRepository.findByFloorID(shop.getFloorID());
			Set<Shops> shops = floorObj.getShops();
			if(shops!=null){
				shops.add(shop);
				floorObj.setShops(shops);
			}
			else{
				Set<Shops> newShops = new HashSet<Shops>();
				newShops.add(shop);
				floorObj.setShops(newShops);
			}
			
			
			floorRepository.save(floorObj);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Shops findByShopNumber(String shopNumber) {
		return shopRepository.findByShopNumber(shopNumber);
		
	}
	
	
}
