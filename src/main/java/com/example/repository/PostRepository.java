package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Post;

@Repository
@Transactional
public class PostRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<Post> POST_ROW_MAPPER = (rs, i) ->{
		Post post = new Post();
		post.setId(rs.getInt("id"));
		post.setComment(rs.getString("comment"));
		post.setImage(rs.getString("image"));
		post.setGroupId(rs.getInt("group_id"));
		
		return post;
	};
	
	public void posting(Post post) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(post);
		
		String sql = "INSERT INTO posts(comment, image, group_id) VALUES(:comment, :image, :groupId);";
		
		template.update(sql, param);
	}
	
	public List<Post> findByGroupId(String groupId){
		String sql = "SELECT comment, image, group_id FROM posts WHERE group_id=:groupId ORDER BY id;";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("groupId", groupId);
		
		List<Post> postList = template.query(sql, param, POST_ROW_MAPPER);
		
		return postList;
	}
}
