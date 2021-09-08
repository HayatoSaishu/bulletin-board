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
	
	private static final RowMapper<Comment> POST_ROW_MAPPER = (rs, i) ->{
		Comment comment = new Comment();
		comment.setId(rs.getInt("id"));
		comment.setContent(rs.getString("content"));
		comment.setArticleId(rs.getInt("article_id"));
		
		return comment;
	};
	
	public void posting(Comment post) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(post);
		
		String sql = "INSERT INTO posts(comment, image, group_id) VALUES(:comment, :image, :groupId);";
		
		template.update(sql, param);
	}
	
	public List<Comment> findByGroupId(String groupId){
		String sql = "SELECT comment, image, group_id FROM posts WHERE group_id=:groupId ORDER BY id;";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("groupId", groupId);
		
		List<Comment> postList = template.query(sql, param, POST_ROW_MAPPER);
		
		return postList;
	}
}
