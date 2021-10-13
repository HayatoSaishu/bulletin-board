package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Comment;
import com.example.repository.CommentRepository;

@Service
@Transactional
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	/**
	 * コメントを投稿する.
	 * 
	 * @param comment コメント
	 */
	public void postComment(Comment comment) {
		commentRepository.insert(comment);
	}
	
	public List<Comment> seachByArticleId(Integer articleId){
		return commentRepository.findByArticleId(articleId);
	}
}
