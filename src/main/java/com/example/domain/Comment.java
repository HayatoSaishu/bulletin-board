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
	 * コメント投稿者の名前
	 */
	private String userName;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", content=" + content + ", articleId=" + articleId + ", userName=" + userName
				+ "]";
	}
}
