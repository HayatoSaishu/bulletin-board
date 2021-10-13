package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Article;
import com.example.repository.ArticleRepository;

/**
 * スレッド情報の業務処理を行うサービス.
 * 
 * @author hayato.saishu
 *
 */
@Service
@Transactional
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepository;

	/**
	 * スレッド情報を全件取得する.
	 * 
	 * @return スレッド情報
	 */
	public List<Article> searchAll() {
		List<Article> articleList = articleRepository.findAll();

		return articleList;
	}

	public List<Article> searchByNameLike(String name) {
		List<Article> articleList = new ArrayList<>();
		if (name.length() == 0) {
			articleList = articleRepository.findAll();
		} else {
			articleList = articleRepository.findByName(name);
		}

		return articleList;
	}

	/**
	 * スレッドを作成する.
	 * 
	 * @param article スレッド情報
	 */
	public void createArticle(Article article) {
		articleRepository.insert(article);
	}
}
