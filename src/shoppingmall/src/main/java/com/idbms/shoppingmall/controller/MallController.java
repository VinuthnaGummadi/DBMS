package com.idbms.shoppingmall.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.idbms.shoppingmall.model.Floors;
import com.idbms.shoppingmall.model.Mall;
import com.idbms.shoppingmall.model.Role;
import com.idbms.shoppingmall.model.Shops;
import com.idbms.shoppingmall.model.User;
import com.idbms.shoppingmall.service.MallService;
import com.idbms.shoppingmall.service.UserService;
import com.idbms.shoppingmall.util.WebResponse;

@Controller
public class MallController {

	@Autowired
	MallService mallService;
	
	@Autowired
	UserService userService;
	
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
		Mall mall = mallService.findByMallID(1);
		modelAndView.addObject("mall", mall);
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
		Mall mall = mallService.findByMallID(1);
		modelAndView.addObject("mall", mall);
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
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Role role = userService.findByRole(user.getAuthorities().toString().replace("[", "").replace("]", ""));
		Mall mall = mallService.findMallForShopOwner(user.getUsername(), role.getId());
		modelAndView.addObject("mall", mall);
		Shops editShop = new Shops();
		modelAndView.addObject("editShop",editShop);
		modelAndView.setViewName("/shopOwner/layout");
		return modelAndView;
	}
	
	@RequestMapping(value={"/shopOwner/updateShop"}, method=RequestMethod.POST)
	public ModelAndView shopOwnerEditShop(@Valid Shops editShop, BindingResult bindingResult){
		ModelAndView modelAndView = new ModelAndView();
		Shops shop = mallService.findByShopNumber(editShop.getShopNumber());
		shop.setShopName(editShop.getShopName());
		shop.setCategory(editShop.getCategory());
		shop.setDescription(editShop.getDescription());
		shop.setRent(editShop.getRent());
		mallService.saveShop(shop);
		
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Role role = userService.findByRole(user.getAuthorities().toString().replace("[", "").replace("]", ""));
		Mall mall = mallService.findMallForShopOwner(user.getUsername(), role.getId());
		modelAndView.addObject("mall", mall);
		editShop = new Shops();
		modelAndView.addObject("editShop",editShop);
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
	
	@ExceptionHandler(BindException.class)
	public @ResponseBody WebResponse handleBindException(BindException be ){
		return new WebResponse(false,
				getBindExceptionMessage(be) // custom method to find and send an appropriate response
		);
	}
	
	public static final String INVALID_DATA_PROVIDED = "Invalid data provided";
	public static final String ID_WRAPPER = "Shops";

	protected String getBindExceptionMessage(BindException be){

	    if(be==null && be.getBindingResult()==null){
	        return INVALID_DATA_PROVIDED;
	    }

	    List<ObjectError> errors = be.getBindingResult().getAllErrors();

	    if(errors==null || errors.isEmpty()){
	        return INVALID_DATA_PROVIDED;
	    }

	    for(ObjectError objectError : errors){
	        if(objectError instanceof FieldError){
	            if(ID_WRAPPER.equalsIgnoreCase(objectError.getObjectName())){
	                return "Invalid 'id' specified";
	            }
	        }
	    }

	    return INVALID_DATA_PROVIDED;

	}
}
