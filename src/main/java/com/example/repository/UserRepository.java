package com.example.repository;

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
	
	private static final RowMapper<User>USER_ROW_MAPPER = (rs, i) ->{
		
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setMailAddress(rs.getString("mail_address"));
		user.setPassword(rs.getString("password"));
		
		return user;
	};
	
	public void signUp (User user) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		
		String  sql = "INSERT INTO users(name, mail_address, password) VALUES(:name, :mailAddress, :password);";
		
		template.update(sql, param);
	}
	
	public User load(Integer id) {
		
		String sql = "SELECT id, name, mail_address, password FROM users WHERE id=:id;";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		
		User user = template.queryForObject(sql, param, USER_ROW_MAPPER);
		
		return user;
	}
}
