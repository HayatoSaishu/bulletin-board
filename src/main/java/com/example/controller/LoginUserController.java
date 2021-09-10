package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginUserController {

    @RequestMapping("/toLogin")
	public String toLogin(Model model, @RequestParam(required = false) String error)  {
		if(error != null){
			System.err.println("login failed");
			model.addAttribute("errorMessage", "メールアドレスまたはパスワード違います");
		}
		return "user/login";
	}
}
