package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.LoginUser;
import com.example.domain.User;
import com.example.form.SignUpUserForm;
import com.example.repository.UserRepository;
import com.example.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;

	@ModelAttribute
	public SignUpUserForm setUpSignUpUserForm() {
		return new SignUpUserForm();
	}

	/**
	 * ユーザー登録ページを表示.
	 * 
	 * @return ユーザー登録ページ	
	 */
	@RequestMapping("")
	public String index() {
		return "user/sign-up";
	}

	/**
	 * ユーザーを登録する.
	 * 
	 * @param form フォーム
	 * @param bindingResult 入力値エラーを表示
	 * @return ログインページに遷移
	 */
	@RequestMapping("/sign-up")
	public String signUp(@Validated SignUpUserForm form, BindingResult bindingResult) {
//		User user = userService.findByMailAddress(form.getMailAddress());
//		if (user != null) {
//			bindingResult.rejectValue("mailAddress", "", "メールアドレスが既に登録されています");
//			return "redirect:/user";
//		}
		User user = new User();
		
		BeanUtils.copyProperties(form, user);
		userService.signUp(user);
		return "redirect:/user/toLogin";
	}

	@RequestMapping("/showUser")
	/**
	 * ユーザー情報ページを表示する.
	 * 
	 * @param model     モデル
	 * @param loginUser ログインユーザー情報
	 * @return
	 */
	public String showUser(Model model, @AuthenticationPrincipal LoginUser loginUser) {
		User user = userRepository.load(loginUser.getUser().getId());

		model.addAttribute("user", user);
		return "user/user-detail";
	}
	
	/**
	 * プロフィール更新ページを表示.
	 * 
	 * @param model　モデル
	 * @param id　ID
	 * @return プロフィール更新ページ
	 */
	@RequestMapping("/to-update")
	public String toUpdateProfile(Model model, Integer id) {
		User user = userRepository.load(id);
		model.addAttribute("user", user);
		return "user/profile-update";
	}
	
	/**
	 * プロフィールを更新する.
	 * 
	 * @param id ID
	 * @param name 名前
	 * @param profile　プロフィール
	 */
	@RequestMapping("/update")
	public String updateProfile(RedirectAttributes redirectAttributes, Integer id, String name, String profile) {
		userService.updateProfile(id, name, profile);
		redirectAttributes.addFlashAttribute("updateMessage", "プロフィールを更新しました");
		
		return "redirect:/user/showUser";
	}

}
