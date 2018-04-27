package com.idbms.shoppingmall.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.idbms.shoppingmall.model.Bid;
import com.idbms.shoppingmall.model.Floors;
import com.idbms.shoppingmall.model.Mall;
import com.idbms.shoppingmall.model.Role;
import com.idbms.shoppingmall.model.User;
import com.idbms.shoppingmall.repository.FloorRepository;
import com.idbms.shoppingmall.repository.MallRepository;
import com.idbms.shoppingmall.repository.RoleRepository;
import com.idbms.shoppingmall.repository.ShopRepository;
import com.idbms.shoppingmall.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
    private RoleRepository roleRepository;
	@Autowired
    private MallRepository mallRepository;
	@Autowired
	private FloorRepository floorRepository;
	@Autowired
	private ShopRepository shopRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Value("${spring.queries.user-query}")
	private String userQuery;
    
    @PersistenceContext
    EntityManager entityManager;
	
	@Override
	public User findUserByEmail(String email,int role) {
		Query query = entityManager.createNativeQuery(userQuery,User.class);
        query.setParameter(1, email);
        query.setParameter(2, role);
        
        User user = query.getResultList().isEmpty()?null: (User) query.getResultList().get(0);
        
        if(user!=null){
        	Set<Bid> bids = user.getBids();
    		for(Bid bid:bids){
    			Floors floor = floorRepository.findByFloorID(bid.getShop().getFloorID());
    			bid.setFloorLevel(floor.getFloorLevel());
    		}
    		user.setBids(bids);
        }

		
        return user;
	}

	@Override
	public void saveUser(User user,String role) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole(role.toUpperCase());
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        
        Mall mall = mallRepository.findByMallId(1);
        user.setMall(mall);
        
        
		userRepository.save(user);
	}

	@Override
	public Role findByRole(String role) {
		return roleRepository.findByRole(role);
	}
	
	

}
