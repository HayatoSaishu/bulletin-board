package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
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
	
	/**記事を作成する.
	 * 
	 * @param article 記事情報
	 */
	public void createArticle(Article article) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);
		
		String sql = "INSERT INTO articles(name, content) VALUES(:name, :content)";
		
		template.update(sql, param);
	}
	
	/**
	 * 記事情報を全件取得する.
	 * 
	 * @return 記事情報リスト
	 */
	public List<Article> showArticle(){
		String sql = "SELECT id, name ,content FROM articles ORDER BY id";
		
		List<Article> articleList = template.query(sql, ARTICLE_ROW_MAPPER);
		
		return articleList;
	}
}
