
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

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.domain.User;

@Repository
public class UserRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {

		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setMailAddress(rs.getString("mail_address"));
		user.setPassword(rs.getString("password"));
		user.setProfile(rs.getString("profile"));

		return user;
	};

	private static final ResultSetExtractor<List<User>> USER_RESULT_SET_EXTRACTOR = (rs) -> {
		List<User> userList = new ArrayList<>();
		List<Article> articleList = new ArrayList<>();
		List<Comment> commentList = new ArrayList<>();
		Integer beforeArticleId = 0;
		Integer beforeUserId = 0;

		while (rs.next()) {

			if (rs.getInt("c_id") != 0) {
				Comment comment = new Comment();

				comment.setId(rs.getInt("c_id"));
				comment.setContent(rs.getString("c_content"));
				comment.setArticleId(rs.getInt("c_article_id"));
				comment.setUserId(rs.getInt("c_user_id"));

				commentList.add(comment);

			}

			Integer nowArticleId = rs.getInt("a_id");
			if (nowArticleId != beforeArticleId) {
				Article article = new Article();
				List<Comment> addCommentList = new ArrayList<>();

				article.setId(rs.getInt("a_id"));
				article.setName(rs.getString("a_name"));
				article.setContent(rs.getString("a_content"));
				article.setUserId(rs.getInt("a_user_id"));
				for (Comment comment : commentList) {
					if (comment.getArticleId() == nowArticleId) {
						addCommentList.add(comment);
					}
				}
				article.setCommentList(addCommentList);
				commentList = new ArrayList<>();

				articleList.add(article);
			}

			Integer nowUserId = rs.getInt("c_user_id");
			if (nowUserId != beforeUserId) {
				User user = new User();

				user.setId(rs.getInt("u_id"));
				user.setName(rs.getString("u_name"));
				user.setMailAddress(rs.getString("u_mail_address"));
				user.setPassword(rs.getString("u_password"));
				user.setProfile(rs.getString("u_profile"));
				user.setImage(rs.getString("u_image"));
				user.setArticleList(articleList);

				userList.add(user);
			}

			beforeArticleId = nowArticleId;
			beforeUserId = nowUserId;
		}

		return userList;
	};

	/**
	 * ユーザー登録をする.
	 * 
	 * @param user ユーザー
	 */
	public void signUp(User user) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);

		String sql = "INSERT INTO users(name, mail_address, password) VALUES(:name, :mailAddress, :password);";

		template.update(sql, param);
	}

	/**
	 * ユーザー情報を1件取得する.
	 * 
	 * @param id ユーザーID
	 * @return ユーザー情報
	 */
	public User load(Integer id) {

		String sql = "SELECT id, name, mail_address, password, profile, image FROM users WHERE id=:id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		User user = template.queryForObject(sql, param, USER_ROW_MAPPER);

		return user;
	}

	/**
	 * メールアドレスとパスワードからユーザー情報を取得する.
	 * 
	 * @param mailAddress メールアドレス
	 * @param password    パスワード
	 * @return ユーザー情報
	 */
	public User findByMailAddressAndPassword(String mailAddress, String password) {

		String sql = "SELECT id, name, mail_address, password, profile, image FROM users WHERE mail_address=:mailAddress AND password=:password;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress).addValue("password",
				password);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);

		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}

	/**
	 * メールアドレスからユーザー情報を取得する.
	 * 
	 * @param mailAddress メールアドレス
	 * @return ユーザー情報
	 */
	public User findByMailAddress(String mailAddress) {
		String sql = "SELECT id, name, mail_address, password, profile, image FROM users WHERE mail_address=:mailAddress;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress);

		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);

		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}

	/**
	 * ユーザー情報を更新する.
	 * 
	 * @param user ユーザー
	 */
	public void updateProfile(User user) {
		String sql = "UPDATE users SET name=:name, profile=:profile, image=:image WHERE id=:id";

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", user.getId())
				.addValue("name", user.getName()).addValue("profile", user.getProfile()).addValue("image", user.getImage());

		template.update(sql, param);
	}

	/**
	 * ユーザー情報をコメントとスレッドを含めて取得する.
	 * 
	 * @param id ID
	 * @return ユーザー情報
	 */
	public User loadUserAndArticleAndComment(Integer id) {
		String sql = "SELECT u.id u_id, u.name u_name, u.mail_address u_mail_address, U.password u_password, u.profile u_profile,"
				+ " u.image u_image, a.id a_id, a.name a_name, a.content a_content, a.user_id a_user_id, c.id c_id,"
				+ " c.content c_content, c.article_id c_article_id, c.user_id c_user_id"
				+ " FROM users AS u LEFT OUTER JOIN articles AS a ON u.id=a.user_id LEFT OUTER JOIN comments AS c ON a.id=c.article_id WHERE u.id=:id"
				+ " ORDER BY c.id DESC;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		List<User> userList = template.query(sql, param, USER_RESULT_SET_EXTRACTOR);
//		if (userList.get(0).getCommentList().size() == 0) {
//			return null;
//		}
		if (userList.get(0).getArticleList().size() == 0) {
			return null;
		}

		return userList.get(0);
	}
}
