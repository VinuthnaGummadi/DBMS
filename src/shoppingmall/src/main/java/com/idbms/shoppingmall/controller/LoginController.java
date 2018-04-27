package com.idbms.shoppingmall.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.idbms.shoppingmall.model.User;
import com.idbms.shoppingmall.service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value={"/admin/login"}, method = RequestMethod.GET)
	public ModelAndView adminLogin(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/login");
		return modelAndView;
	}
	
	@RequestMapping(value={"/shopOwner/login"}, method = RequestMethod.GET)
	public ModelAndView shopOwnerLogin(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("shopOwner/login");
		return modelAndView;
	}
	
	
	@RequestMapping(value="/admin/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("admin/registration");
		return modelAndView;
	}
	
	@RequestMapping(value = "/admin/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail(),1);
		if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("admin/registration");
		} else {
			userService.saveUser(user,"ADMIN");
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("admin/registration");
			
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/shopOwner/registration", method = RequestMethod.GET)
	public ModelAndView shopOwnerRegistration(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("shopOwner/registration");
		return modelAndView;
	}
	
	@RequestMapping(value = "/shopOwner/registration", method = RequestMethod.POST)
	public ModelAndView createNewUserShopOwner(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail(),2);
		if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("shopOwner/registration");
		} else {
			userService.saveUser(user,"SHOP_OWNER");
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("shopOwner/registration");
			
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/customer/registration", method = RequestMethod.GET)
	public ModelAndView customerRegistration(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("customer/registration");
		return modelAndView;
	}
	
	@RequestMapping(value = "/customer/registration", method = RequestMethod.POST)
	public ModelAndView createNewUserCustomer(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail(),3);
		if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("customer/registration");
		} else {
			userService.saveUser(user,"CUSTOMER");
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("customer/registration");
			
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/home", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName(),1);
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
		modelAndView.setViewName("admin/home");
		return modelAndView;
	}
	
	@RequestMapping(value="/shopOwner/home", method = RequestMethod.GET)
	public ModelAndView shopOwnerhome(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName(),2);
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage","Content Available Only for Users with Shop Owner Role");
		modelAndView.setViewName("shopOwner/home");
		return modelAndView;
	}
	
	@RequestMapping(value="/customer/home", method = RequestMethod.GET)
	public ModelAndView customerhome(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName(),3);
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage","Content Available Only for Users with Customer Role");
		modelAndView.setViewName("customer/home");
		return modelAndView;
	}

}