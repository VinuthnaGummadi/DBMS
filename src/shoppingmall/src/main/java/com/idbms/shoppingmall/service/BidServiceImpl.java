package com.idbms.shoppingmall.service;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transaction;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.idbms.shoppingmall.model.Bid;
import com.idbms.shoppingmall.model.Floors;
import com.idbms.shoppingmall.model.Shops;
import com.idbms.shoppingmall.model.User;
import com.idbms.shoppingmall.repository.BidRepository;
import com.idbms.shoppingmall.repository.FloorRepository;
import com.idbms.shoppingmall.repository.MallRepository;
import com.idbms.shoppingmall.repository.ShopRepository;
import com.idbms.shoppingmall.repository.UserRepository;

@Service("bidService")
public class BidServiceImpl implements BidService {

	
	@Autowired
	private FloorRepository floorRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BidRepository bidRepository;
	
	@Value("${spring.queries.bids-delete}")
	private String deleteBIDSQuery;
	
	@Value("${spring.queries.bid-delete}")
	private String deleteBIDQuery;
	
	@Value("${spring.queries.bids-add}")
	private String addBIDSQuery;
	
	@Value("${spring.queries.bid-add}")
	private String addBIDQuery;
	
	@Value("${spring.queries.user-query}")
	private String userQuery;
	
	@PersistenceContext
    EntityManager entityManager;
	
	@Override
	public void addBid(Bid bid,User user ) {
		try {
			Floors floor = floorRepository.findByFloorLevel(bid.getFloorLevel());
			Set<Shops> shops = floor.getShops();
			Shops bidShop = null;
			for(Shops shop:shops){
				if(bid.getShop().getShopNumber().equalsIgnoreCase(shop.getShopNumber())){
					bidShop = shop;
				}
			}
			bid.setShop(bidShop);
			bid.setAuthorized(false);
			
			bidRepository.save(bid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void deleteBid(Integer bidID,int userID) {
		
		Query query = entityManager.createNativeQuery(deleteBIDSQuery);
        query.setParameter(1, bidID);
        query.setParameter(2, userID);
        
        query.executeUpdate();
        
        query = entityManager.createNativeQuery(deleteBIDQuery);
        query.setParameter(1, bidID);
        
        query.executeUpdate();
	}

	@Override
	public Bid findByBidID(Integer bidID) {
		
		return bidRepository.findByBidID(bidID);
	}

	@Override
	public void saveBid(Bid bid, User user) {
		try {
			
			Query query = entityManager.createNativeQuery(userQuery,User.class);
	        query.setParameter(1, user.getEmail());
	        query.setParameter(2, 2);
	        
	        user = query.getResultList().isEmpty()?null: (User) query.getResultList().get(0);
	        
	        if(user!=null){
	        	Set<Bid> bids = user.getBids();
	        	bids.add(bid);
	    		user.setBids(bids);
	        }
	        
	        userRepository.save(user);

			
			/*Query query = entityManager.createNativeQuery(addBIDQuery);
	        query.setParameter(1, bid.getBidID());
	        query.setParameter(2, bid.isAuthorized());
	        query.setParameter(3, bid.getEndDate());
	        query.setParameter(4, bid.getStartDate());
	        query.setParameter(5, bid.getShop().getShopID());
	        query.setParameter(6, bid.getRent());
	        
	        query.executeUpdate();
	        
	        entityManager.flush();
	        
			query = entityManager.createNativeQuery(addBIDSQuery);
	        query.setParameter(1, bid.getBidID());
	        query.setParameter(2, user.getId());
	        
	        query.executeUpdate();
	        
	        entityManager.flush();*/
	        
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
