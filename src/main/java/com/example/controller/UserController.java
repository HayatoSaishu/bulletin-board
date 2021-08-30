package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.User;
import com.example.form.LoginForm;
import com.example.form.SignUpUserForm;
import com.example.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@ModelAttribute
	public SignUpUserForm setUpSignUpUserForm() {
		return new SignUpUserForm();
	}
	
	@RequestMapping("")
	public String index() {
		return "user/sign-up";
	}
	
	@RequestMapping("/sign-up")
	public String signUp(SignUpUserForm form) {
		User user = new User();
		BeanUtils.copyProperties(form, user);
		
		userService.signUp(user);
		
		return "redirect:/user/toLogin";
	}
	
	@RequestMapping("toLogin")
	public String toLogin() {
		return "user/login";
	}
	
	@RequestMapping("/login")
	public  String login(LoginForm form, Model model, RedirectAttributes redirectAttributes) {
		User user = userService.login(form.getMailAddress(), form.getPassword());
		if(user == null) {
			return toLogin();
		}
		redirectAttributes.addFlashAttribute("user", user);
		return "redirect:/user/showUser";
	}
	
	@RequestMapping("/showUser")
	public String showUser() {
		return "user-detail";
	}
	
}
