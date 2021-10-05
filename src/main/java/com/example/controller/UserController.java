package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;
import com.example.domain.User;
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
	
	
	@RequestMapping("/showUser")
	/**
	 * ユーザー情報ページを表示する.
	 * 
	 * @param model モデル
	 * @param loginUser　ログインユーザー情報
	 * @return
	 */
	public String showUser(Model model, @AuthenticationPrincipal LoginUser loginUser) {
		User user = loginUser.getUser();

		model.addAttribute("user", user);
		return "user-detail";
	}
	
}
