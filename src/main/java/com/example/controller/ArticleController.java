package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.domain.LoginUser;
import com.example.form.ArticleForm;
import com.example.service.ArticleService;

/**
 * スレッド情報を操作するコントローラー.
 * 
 * @author hayato.saishu
 *
 */
@Controller
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;
	
	@ModelAttribute
	private ArticleForm setUpForm() {
		return new ArticleForm();
	}

	/**
	 * スレッド情報を全件取得.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/show")
	public String showArticle(Model model) {
		List<Article> articleList = articleService.searchAll();

		if (articleList == null) {
			model.addAttribute("blankMessage", "スレッドがありません");
			return "article/show";
		}
		
		if (articleList.size() == 0) {
			model.addAttribute("blankMessage", "スレッドがありません");
			return "article/show";
		}

		model.addAttribute("articleList", articleList);

		return "article/show";
	}
	
	/**
	 * スレッドを作成する.
	 * 
	 * @param form フォーム
	 */
	@RequestMapping("/create")
	public String createArticle(Model model, @Validated ArticleForm form, BindingResult result, 
									@AuthenticationPrincipal LoginUser loginUser) {
		if(result.hasErrors()) {
			return toCreateArticle(model);
		}
		Article article = new Article();
		article.setName(form.getName());
		article.setContent(form.getContent());
		article.setUserName(loginUser.getUser().getName());
		
		articleService.createArticle(article);
		
		return "redirect:/article/show";
	}
	
	/**
	 * スレッド作成ページに遷移.
	 * 
	 * @param model　モデル
	 * @return　スレッド作成ページ
	 */
	@RequestMapping("/create-page")
	public String toCreateArticle(Model model) {
		return "article/create-article";
	}
	
	@RequestMapping("/search-name")
	public String searchByNameLike(String name, Model model) {
		List<Article> articleList = articleService.searchByNameLike(name);
		
		if(articleList == null) {
			articleList = articleService.searchAll();
			model.addAttribute("noNameMessage", "検索条件に当てはまるスレッドがありませんでした");
		}
		if(articleList.size() == 0) {
			articleList = articleService.searchAll();
			model.addAttribute("noNameMessage", "検索条件に当てはまるスレッドがありませんでした");
		}
		
		model.addAttribute("articleList", articleList);
		
		return "article/show";
	}
}
