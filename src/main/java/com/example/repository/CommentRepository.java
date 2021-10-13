package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Comment;

@Repository
public class CommentRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Comment> COMMENT_ROW_MAPPER = (rs, i) -> {
		Comment comment = new Comment();
		comment.setId(rs.getInt("id"));
		comment.setContent(rs.getString("content"));
		comment.setArticleId(rs.getInt("article_id"));
		comment.setUserName(rs.getString("user_name"));

		return comment;
	};

	/**
	 * コメントを投稿する/
	 * 
	 * @param comment コメント
	 */
	public void insert(Comment comment) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(comment);

		String sql = "INSERT INTO comments(content, article_id, user_name) VALUES(:content, :articleId, :userName);";

		template.update(sql, param);
	}

	/**
	 * コメントをスレッドIDから取得する.
	 * 
	 * @param articleId スレッドID
	 * @return コメントリスト
	 */
	public List<Comment> findByArticleId(Integer articleId) {
		String sql = "SELECT id, content, article_id, user_name FROM comments WHERE article_id=:articleId ORDER BY id DESC;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);

		List<Comment> commentList = template.query(sql, param, COMMENT_ROW_MAPPER);
		if (commentList.size() == 0) {
			return null;
		}

		return commentList;
	}
}
