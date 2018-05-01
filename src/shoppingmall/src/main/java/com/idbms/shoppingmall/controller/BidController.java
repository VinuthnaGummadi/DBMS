package com.idbms.shoppingmall.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.idbms.shoppingmall.model.Bid;
import com.idbms.shoppingmall.model.Role;
import com.idbms.shoppingmall.model.Shops;
import com.idbms.shoppingmall.model.User;
import com.idbms.shoppingmall.repository.FloorRepository;
import com.idbms.shoppingmall.service.BidService;
import com.idbms.shoppingmall.service.MallService;
import com.idbms.shoppingmall.service.UserService;

@Controller
public class BidController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	BidService bidService;
	
	@Autowired
	MallService mallService;
	
	@RequestMapping(value={"/admin/bids"}, method = RequestMethod.GET)
	public ModelAndView adminBids(){
		ModelAndView modelAndView = new ModelAndView();
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Role role = userService.findByRole(user.getAuthorities().toString().replace("[", "").replace("]", ""));
		User users = userService.findUserByEmail(user.getUsername(), role.getId());
		users.setBids(userService.findAllBids());
		
		modelAndView.addObject("user",users);
		
		Bid bid = new Bid();
		modelAndView.addObject("addBid",bid);
		modelAndView.setViewName("/admin/bids");
		return modelAndView;
	}
	
	@RequestMapping(value={"/admin/addBid"}, method = RequestMethod.POST)
	public ModelAndView addBid(@Valid Bid addBid, BindingResult bindingResult){
		ModelAndView modelAndView = new ModelAndView();
		
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Role role = userService.findByRole(user.getAuthorities().toString().replace("[", "").replace("]", ""));
		User users = userService.findUserByEmail(user.getUsername(), role.getId());
		
		bidService.addBid(addBid,users);
		
		users = userService.findUserByEmail(user.getUsername(), role.getId());
		modelAndView.addObject("user",users);
		addBid = new Bid();
		modelAndView.addObject("addBid",addBid);
		modelAndView.setViewName("/admin/bids");
		return modelAndView;
	}
	
	
	@RequestMapping(value={"/admin/editBid"}, method = RequestMethod.GET)
	public ModelAndView saveBid(@Valid User user, BindingResult bindingResult,@RequestParam(value="bidID", required=true) Integer bidID,
			@RequestParam(value="startDate", required=true) String startDate,
			@RequestParam(value="endDate", required=true) String endDate,
			@RequestParam(value="floorLevel", required=true) String floorLevel,
			@RequestParam(value="shopNumber", required=true) String shopNumber,
			@RequestParam(value="minRent", required=true) Double minRent,
			@RequestParam(value="rent", required=true) Double rent){
		ModelAndView modelAndView = new ModelAndView();
		
		org.springframework.security.core.userdetails.User user1 = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Role role = userService.findByRole(user1.getAuthorities().toString().replace("[", "").replace("]", ""));
		user = userService.findUserByEmail(user1.getUsername(), role.getId());
		
		Bid editBid = bidService.findByBidID(bidID);
		
		editBid.setEndDate(endDate);
		editBid.setStartDate(startDate);
		editBid.setFloorLevel(floorLevel);
		
		Shops shop = mallService.findByShopNumber(shopNumber);
		shop.setMinRent(minRent);
		shop.setRent(rent);
		editBid.setShop(shop);
		editBid.setAuthorized(false);
		
		bidService.saveBid(editBid,user);
		
		user = userService.findUserByEmail(user1.getUsername(), role.getId());
		modelAndView.addObject("user",user);
		
		Bid addBid = new Bid();
		modelAndView.addObject("addBid",addBid);
		modelAndView.setViewName("/admin/bids");
		return modelAndView;
	}
	
	@RequestMapping(value={"/admin/deleteBid"}, method = RequestMethod.GET)
	@Transactional
	(
	    propagation = Propagation.REQUIRED, 
	    readOnly = false,
	    rollbackFor = Throwable.class
	)
	public ModelAndView deleteBid(@Valid User user, BindingResult bindingResult,@RequestParam(value="id", required=true) Integer id){
		ModelAndView modelAndView = new ModelAndView();
		
		org.springframework.security.core.userdetails.User user1 = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Role role = userService.findByRole(user1.getAuthorities().toString().replace("[", "").replace("]", ""));
		user = userService.findUserByEmail(user1.getUsername(), role.getId());
		
		bidService.deleteBid(id,user.getId());
		
		modelAndView.addObject("user",user);
		Bid addBid = new Bid();
		modelAndView.addObject("addBid",addBid);
		modelAndView.setViewName("/admin/bids");
		return modelAndView;
	}
	
	@RequestMapping(value={"/admin/authorizeBid"},method=RequestMethod.GET)
	public ModelAndView authorizeBid(@Valid User user,BindingResult bindingResult,@RequestParam(value="id", required=true) Integer id){
		ModelAndView modelAndView = new ModelAndView();
		
		
		org.springframework.security.core.userdetails.User user1 = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Role role = userService.findByRole(user1.getAuthorities().toString().replace("[", "").replace("]", ""));
		User users = userService.findUserByEmail(user1.getUsername(), role.getId());
		
		
		Bid bid = bidService.findByBidID(id);
		bid.setAuthorized(true);
		bid.getShop().setRent(bid.getRent());
		bid.getShop().setShopOccupied(true);
		bidService.saveBid(bid, users);
		
		users.setBids(userService.findAllBids());
		
		modelAndView.addObject("user",users);
		
		Bid addBid = new Bid();
		modelAndView.addObject("addBid",addBid);
		modelAndView.setViewName("/admin/bids");
		
		return modelAndView;
	}
	
	@RequestMapping(value={"/shopOwner/bids"}, method = RequestMethod.GET)
	public ModelAndView shopOwnerBids(){
		ModelAndView modelAndView = new ModelAndView();
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Role role = userService.findByRole(user.getAuthorities().toString().replace("[", "").replace("]", ""));
		User users = userService.findUserByEmail(user.getUsername(), role.getId());
		
		User adminUser = userService.findUserByEmail("vinuthna.gummadi@gmail.com", 1);
		
		Set<Bid> bids = users.getBids();
		bids.addAll(adminUser.getBids());
		users.setBids(bids);
		
		modelAndView.addObject("user",users);
		
		Bid bid = new Bid();
		modelAndView.addObject("addBid",bid);
		modelAndView.setViewName("/shopOwner/bids");
		return modelAndView;
	}
	
	@RequestMapping(value={"/shopOwner/editBid"}, method = RequestMethod.GET)
	@Transactional
	(
	    propagation = Propagation.REQUIRED, 
	    readOnly = false,
	    rollbackFor = Throwable.class
	)
	public ModelAndView saveShopOwnerBid(@Valid User user, BindingResult bindingResult,@RequestParam(value="bidID", required=true) Integer bidID,
			@RequestParam(value="rent", required=true) Double rent){
		ModelAndView modelAndView = new ModelAndView();
		
		org.springframework.security.core.userdetails.User user1 = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Role role = userService.findByRole(user1.getAuthorities().toString().replace("[", "").replace("]", ""));
		user = userService.findUserByEmail(user1.getUsername(), role.getId());
		
		Bid editBid = bidService.findByBidID(bidID);
		
		Bid addBid = new Bid();
		
		addBid.setRent(rent);
		
		Random random = new Random();
		
		//addBid.setBidID(random.nextInt(100)+1);
		addBid.setAuthorized(false);
		addBid.setEndDate(editBid.getEndDate());
		addBid.setStartDate(editBid.getStartDate());
		/*Shops shop = new Shops();
		shop.setShopID(editBid.getShop().getShopID());
		shop.setCategory(edit);*/
		addBid.setShop(editBid.getShop());
		
		bidService.saveBid(addBid,user);
		
		user = userService.findUserByEmail(user1.getUsername(), role.getId());
		modelAndView.addObject("user",user);
		
		addBid = new Bid();
		modelAndView.addObject("addBid",addBid);
		modelAndView.setViewName("/shopOwner/bids");
		return modelAndView;
	}
	
}
