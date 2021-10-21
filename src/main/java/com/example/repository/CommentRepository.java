package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Comment;
import com.example.domain.User;

@Repository
public class CommentRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Comment> COMMENT_ROW_MAPPER = (rs, i) -> {
		Comment comment = new Comment();
		comment.setId(rs.getInt("id"));
		comment.setContent(rs.getString("content"));
		comment.setArticleId(rs.getInt("article_id"));
		comment.setUserId(rs.getInt("user_id"));

		return comment;
	};

	private static final ResultSetExtractor<List<Comment>> COMMENT_RESULT_SET_EXTRACTOR = (rs) -> {
		List<Comment> commentList = new ArrayList<>();

		while (rs.next()) {
			if (rs.getInt("c_id") != 0) {
				User user = new User();
				user.setId(rs.getInt("u_id"));
				user.setName(rs.getString("u_name"));
				user.setProfile(rs.getString("u_profile"));
				user.setMailAddress(rs.getString("u_mail_address"));
				user.setPassword(rs.getString("u_password"));

				Comment comment = new Comment();
				comment.setId(rs.getInt("c_id"));
				comment.setContent(rs.getString("c_content"));
				comment.setArticleId(rs.getInt("c_article_id"));
				comment.setUserId(rs.getInt("c_user_id"));
				comment.setUser(user);

				commentList.add(comment);
			}
		}
		return commentList;
	};

	/**
	 * コメントを投稿する/
	 * 
	 * @param comment コメント
	 */
	public void insert(Comment comment) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(comment);

		String sql = "INSERT INTO comments(content, article_id, user_id) VALUES(:content, :articleId, :userId);";

		template.update(sql, param);
	}

	/**
	 * コメントをスレッドIDから取得する.
	 * 
	 * @param articleId スレッドID
	 * @return コメントリスト
	 */
	public List<Comment> findByArticleId(Integer articleId) {
		String sql = "SELECT c.id c_id, c.content c_content, c.article_id c_article_id, c.user_id c_user_id, u.id u_id"
				+ ", u.name u_name, u.profile u_profile, u.mail_address u_mail_address, u.password u_password"
				+ " FROM comments AS c INNER JOIN users AS u ON c.user_id = u.id "
				+ "WHERE c.article_id=:articleId ORDER BY c.id DESC;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);

		List<Comment> commentList = template.query(sql, param, COMMENT_RESULT_SET_EXTRACTOR);
		if (commentList.size() == 0) {
			return null;
		}

		return commentList;
	}

	/**
	 * ユーザーIDからコメントリスト取得する.
	 * 
	 * @param id ID
	 * @return コメントリスト
	 */
	public List<Comment> findByUserId(Integer id) {
		String sql = "SELECT id, content, article_id, user_id FROM comments WHERE user_id=:id ORDER BY id DESC;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		List<Comment> commentList = template.query(sql, param, COMMENT_ROW_MAPPER);
		if (commentList.size() == 0) {
			return null;
		}

		return commentList;
	}
}
