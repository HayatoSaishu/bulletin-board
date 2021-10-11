
package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

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
		user.setArticleId(rs.getInt("article_id"));
		user.setCommentId(rs.getInt("comment_id"));

		return user;
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

		String sql = "SELECT id, name, mail_address, password, article_id, comment_id FROM users WHERE id=:id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		User user = template.queryForObject(sql, param, USER_ROW_MAPPER);

		return user;
	}

	/**
	 * メールアドレスとパスワードからユーザー情報を取得する.
	 * 
	 * @param mailAddress メールアドレス
	 * @param password　パスワード
	 * @return　ユーザー情報
	 */
	public User findByMailAddressAndPassword(String mailAddress, String password) {

		String sql = "SELECT mail_address, password FROM users WHERE mail_address=:mailAddress AND password=:password;";
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
	 * @return　ユーザー情報
	 */
	public User findByMailAddress(String mailAddress) {
		String sql = "SELECT id, name, mail_address, password, article_id, comment_id, profile FROM users WHERE mail_address=:mailAddress;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);

		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}
	
	public void updateProfile(User user) {
		String sql = "UPDATE users SET name=:name, profile=:profile WHERE id=:id";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", user.getId()).addValue("name", user.getName()).addValue("profile", user.getProfile());
		
		template.update(sql, param);
	}
}
