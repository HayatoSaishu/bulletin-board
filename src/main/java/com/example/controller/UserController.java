package com.example.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.domain.LoginUser;
import com.example.domain.User;
import com.example.form.SignUpUserForm;
import com.example.repository.UserRepository;
import com.example.service.CommentService;
import com.example.service.UserService;

/**
 * ユーザー情報を操作するコントローラ.
 * 
 * @author hayato.saishu
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CommentService commentService;

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
	 * @param form          フォーム
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

	/**
	 * ログインユーザー情報ページを表示する.
	 * 
	 * @param model     モデル
	 * @param loginUser ログインユーザー情報
	 * @return
	 */
	@RequestMapping("/showLoginUser")
	public String showLoginUser(Model model, @AuthenticationPrincipal LoginUser loginUser) {
		User user = userService.loadUserDetail(loginUser.getUser().getId());
		
		List<Article> articleList = new ArrayList<>();
		Map<Article, Integer> map = new HashMap<>();
		
		for (Article article : user.getArticleList()) {
			Integer id = article.getId();
			for(Map.Entry<Article, Integer> entry : map.entrySet()) {
				if(id != entry.getValue());
				map.put(article,id);
				articleList.add(entry.getKey());
				System.out.println(entry.getKey() + "" + entry.getValue());
			}
		}

//		if (user.getCommentList() == null) {
//			model.addAttribute("blankCommentMessage", "投稿がまだありません");
//		}
//		if (user.getCommentList().size() == 0) {
//			model.addAttribute("blankCommentMessage", "投稿がまだありません");
//		}
		if (user.getArticleList() == null) {
			model.addAttribute("blankArticleMessage", "作成したスレッドがまだありません");
		}
		if (user.getArticleList().size() == 0) {
			model.addAttribute("blankArticleMessage", "作成したスレッドがまだありません");
		}

		model.addAttribute("user", user);
		model.addAttribute("displayButton", "");
		model.addAttribute("articleList", articleList);

		return "user/user-detail";
	}

	/**
	 * ユーザー情報を表示する.
	 * 
	 * @param model モデル
	 * @param id    ID
	 * @return ユーザー情報
	 */
	@RequestMapping("/showUser")
	public String showUser(Model model, Integer id, @AuthenticationPrincipal LoginUser loginUser) {
		User user = userRepository.load(id);
		List<Comment> commentList = commentService.searchByUserId(id);

		model.addAttribute("user", user);
		if (user.getId() == loginUser.getUser().getId()) {
			model.addAttribute("displayButton", "");
		}

		if (commentList == null) {
			model.addAttribute("blankMessage", "投稿がまだありません");
			return "user/user-detail";
		}
		if (commentList.size() == 0) {
			model.addAttribute("blankMessage", "投稿がまだありません");
			return "user/user-detail";
		}

		model.addAttribute("commentList", commentList);
		return "user/user-detail";
	}

	/**
	 * プロフィール更新ページを表示.
	 * 
	 * @param model モデル
	 * @param id    ID
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
	 * @param id      ID
	 * @param name    名前
	 * @param profile プロフィール
	 * @throws IOException
	 */
	@RequestMapping("/update")
	public String updateProfile(RedirectAttributes redirectAttributes, Integer id, String name, String profile,
			MultipartFile multipartFile) throws IOException {
		String image = multipartFile.getOriginalFilename();
		Path filePath = Paths.get("C:/env/springworkspace/bulletin-board/src/main/resources/static/image/" + image);
		try {
			// アップロードファイルをバイト値に変換
			byte[] bytes = multipartFile.getBytes();

			// バイト値を書き込む為のファイルを作成して指定したパスに格納
			OutputStream stream = Files.newOutputStream(filePath);

			// ファイルに書き込み
			stream.write(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		userService.updateProfile(id, name, profile, image);
		redirectAttributes.addFlashAttribute("updateMessage", "プロフィールを更新しました");

		return "redirect:/user/showLoginUser";
	}

}
