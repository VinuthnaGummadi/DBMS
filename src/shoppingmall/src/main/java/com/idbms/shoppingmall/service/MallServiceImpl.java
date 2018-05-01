package com.idbms.shoppingmall.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.idbms.shoppingmall.model.Floors;
import com.idbms.shoppingmall.model.Mall;
import com.idbms.shoppingmall.model.Shops;
import com.idbms.shoppingmall.model.User;
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
	
	@Value("${spring.queries.shopOwner-layout}")
	private String userQuery;
	
	@PersistenceContext
    EntityManager entityManager;

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

	@Override
	public Mall findMallForShopOwner(String email, int roleID) {
		
			Mall mall = new Mall();
		try {
			Query query = entityManager.createNativeQuery(userQuery);
	        query.setParameter(1, email);
	        query.setParameter(2, roleID);
	        
	        List<Object[]> rows = query.getResultList();
	        List<Floors> floors = new ArrayList<Floors>();
	        Set<Shops> shops = new HashSet<Shops>();
	        for (Object[] row : rows) {
	        	mall.setMallId((int) row[0]);
	        	mall.setMallName((String) row[1]);
	        	mall.setAddress((String) row[2]);
	        	mall.setPhoneNo((String) row[3]);
	        	Floors floor = new Floors();
	        	floor.setFloorID((int) row[4]);
	        	floor.setFloorLevel((String) row[5]);
	        	floor.setFloorCategory((String) row[6]);
	        	floor.setFloorPlan((String) row[7]);
	        	
	        	Shops shop = new Shops();
	        	shop.setShopID((int) row[8]);
	        	shop.setShopName((String) row[9]);
	        	shop.setShopNumber((String) row[10]);
	        	shop.setCategory((String) row[11]);
	        	shop.setRent((Double)row[12]);
	        	shop.setDescription((String) row[13]);
	        	shops.add(shop);
	        	floor.setShops(shops);
	        	floors.add(floor);
	        	mall.setFloors(floors);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        
		return mall;
	}
	
	
}
