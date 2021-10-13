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
import com.example.domain.Comment;
import com.example.domain.LoginUser;
import com.example.form.CommentForm;
import com.example.service.ArticleService;
import com.example.service.CommentService;

@Controller
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@Autowired
	private ArticleService articleService;

	@ModelAttribute
	private CommentForm setUpForm() {
		return new CommentForm();
	}

	@RequestMapping("/post")
	public String postComment(@Validated CommentForm form, Model model, BindingResult result,
								@AuthenticationPrincipal LoginUser loginUser) {
		if (result.hasErrors()) {
			return toComment(String.valueOf(form.getArticleId()), model, loginUser);
		}

		Comment comment = new Comment();
		comment.setContent(form.getContent());
		comment.setArticleId(form.getArticleId());
		comment.setUserName(form.getUserName());

		commentService.postComment(comment);

		return "redirect:/comment/show";
	}

	@RequestMapping("/show")
	public String toComment(String id, Model model, @AuthenticationPrincipal LoginUser loginUser) {
		Integer articleId = Integer.parseInt(id);
		List<Comment> commentList = commentService.seachByArticleId(articleId);
		Article article = articleService.load(articleId);
		model.addAttribute("article", article);
		model.addAttribute("loginUserName", loginUser.getUser().getName());

		if (commentList == null) {
			model.addAttribute("blankMessage", "投稿がまだありません");
			return "comment/show-comment";
		}
		if (commentList.size() == 0) {
			model.addAttribute("blankMessage", "投稿がまだありません");
			return "comment/show-comment";
		}

		model.addAttribute("commentList", commentList);

		return "comment/show-comment";
	}
}
