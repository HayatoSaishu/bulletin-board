package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;

@Repository
public class ArticleRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Article> ARTICLE_ROW_MAPPER = (rs, i) -> {
		Article article = new Article();
		article.setId(rs.getInt("id"));
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));

		return article;
	};

	/**
	 * スレッドを作成する.
	 * 
	 * @param article 記事情報
	 */
	public void insert(Article article) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);

		String sql = "INSERT INTO articles(name, content, user_name) VALUES(:name, :content, :userName)";

		template.update(sql, param);
	}

	/**
	 * スレッド情報を全件取得する.
	 * 
	 * @return 記事情報リスト
	 */
	public List<Article> findAll() {
		String sql = "SELECT id, name ,content FROM articles ORDER BY id";

		List<Article> articleList = template.query(sql, ARTICLE_ROW_MAPPER);
		if (articleList.size() == 0) {
			return null;
		}

		return articleList;
	}

	/**
	 * スレッドを名前検索する.
	 * 
	 * @param name スレッド名
	 * @return スレッドリスト
	 */
	public List<Article> findByName(String name) {
		String sql = "SELECT id, name, content FROM articles WHERE name ILIKE :name ORDER BY id";

		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%");

		List<Article> articleList = template.query(sql, param, ARTICLE_ROW_MAPPER);
		if (articleList.size() == 0) {
			return null;
		}

		return articleList;
	}
	
	public Article load(Integer id) {
		String sql = "SELECT id, name, content FROM articles WHERE id=:id";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		
		Article article = template.queryForObject(sql, param, ARTICLE_ROW_MAPPER);
		
		return article;
	}
}
