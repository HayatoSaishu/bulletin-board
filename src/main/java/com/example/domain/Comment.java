package com.example.domain;

/**
 * ユーザーが投稿した内容を保存する.
 * 
 * @author h3sbs
 *
 */
public class Comment {

	/**
	 * 投稿ID
	 */
	private Integer id;
	/**
	 * 投稿内容
	 */
	private String content;
	/**
	 * 投稿された掲示板のスレッドID
	 */
	private Integer articleId;
	/**
	 * コメント投稿ユーザーのId
	 */
	private Integer userId;

	/**
	 * コメント投稿ユーザー
	 */
	private User user;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", content=" + content + ", articleId=" + articleId + ", userId=" + userId
				+ ", user=" + user + "]";
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
