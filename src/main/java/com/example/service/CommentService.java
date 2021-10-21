package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Comment;
import com.example.domain.User;
import com.example.repository.CommentRepository;
import com.example.repository.UserRepository;

@Service
@Transactional
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private UserRepository userRepository;

	/**
	 * コメントを投稿する.
	 * 
	 * @param comment コメント
	 */
	public void postComment(Comment comment) {
		commentRepository.insert(comment);
	}

	/**
	 * スレッドIDから投稿コメントを取得.
	 * 
	 * @param articleId スレッドID
	 * @return 投稿リスト
	 */
	public List<Comment> seachByArticleId(Integer articleId) {
		return commentRepository.findByArticleId(articleId);
	}

	/**
	 * 投稿情報からユーザー情報を取得する.
	 * 
	 * @param id ID
	 * @return ユーザー情報
	 */
	public User showUser(Integer id) {
		return userRepository.load(id);
	}

	/**
	 * ユーザーIDからコメントを取得する.
	 * 
	 * @param id ID
	 * @return コメントリスト
	 */
	public List<Comment> searchByUserId(Integer id) {
		return commentRepository.findByUserId(id);
	}
}
