package com.idbms.shoppingmall.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.idbms.shoppingmall.model.Floors;
import com.idbms.shoppingmall.model.Mall;
import com.idbms.shoppingmall.model.Shops;
import com.idbms.shoppingmall.model.User;
import com.idbms.shoppingmall.service.MallService;

@Controller
public class MallController {

	@Autowired
	MallService mallService;
	
	@RequestMapping(value={"/admin/layout"}, method = RequestMethod.GET)
	public ModelAndView adminLayout(){
		ModelAndView modelAndView = new ModelAndView();
		Mall mall = mallService.findByMallID(1);
		modelAndView.addObject("mall", mall);
		Floors newfloor = new Floors();
		modelAndView.addObject("newfloor",newfloor);
		Shops newShop = new Shops();
		modelAndView.addObject("newShop",newShop);
	      
		modelAndView.setViewName("/admin/layout");
		return modelAndView;
	}
	
	@RequestMapping(value={"/admin/addFloor"},method=RequestMethod.POST)
	public ModelAndView addFloor(@Valid Floors newfloor, BindingResult bindingResult){
		ModelAndView modelAndView = new ModelAndView();
		mallService.saveFloor(newfloor);
		modelAndView.addObject("successMessage", "Floor is added successfully");
		modelAndView.addObject("mall", mallService.findByMallID(1));
		newfloor = new Floors();
		modelAndView.addObject("newfloor",newfloor);
		Shops newShop = new Shops();
		modelAndView.addObject("newShop",newShop);
		modelAndView.setViewName("/admin/layout");
		return modelAndView;
	}
	
	@RequestMapping(value={"/admin/addShop"},method=RequestMethod.POST)
	public ModelAndView addShop(@Valid Shops newShop, BindingResult bindingResult){
		ModelAndView modelAndView = new ModelAndView();
		mallService.saveShop(newShop);
		modelAndView.addObject("successMessage", "Shop is added successfully");
		modelAndView.addObject("mall", mallService.findByMallID(1));
		Floors newfloor = new Floors();
		modelAndView.addObject("newfloor",newfloor);
		newShop = new Shops();
		modelAndView.addObject("newShop",newShop);
		modelAndView.setViewName("/admin/layout");
		return modelAndView;
	}
	
	@RequestMapping(value={"/shopOwner/layout"}, method = RequestMethod.GET)
	public ModelAndView shopOwnerLayout(){
		ModelAndView modelAndView = new ModelAndView();
		Mall mall = mallService.findByMallID(1);
		modelAndView.addObject("mall", mall);
	      
		modelAndView.setViewName("/shopOwner/layout");
		return modelAndView;
	}
	@RequestMapping(value={"/customer/layout"}, method = RequestMethod.GET)
	public ModelAndView customerLayout(){
		ModelAndView modelAndView = new ModelAndView();
		Mall mall = mallService.findByMallID(1);
		modelAndView.addObject("mall", mall);
	      
		modelAndView.setViewName("/customer/layout");
		return modelAndView;
	}
}
