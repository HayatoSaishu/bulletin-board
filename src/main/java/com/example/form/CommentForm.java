package com.example.form;

import javax.validation.constraints.NotBlank;

public class CommentForm {

	/**
	 * コメント内容
	 */
	@NotBlank
	private String content;
	/**
	 * スレッドID
	 */
	private Integer articleId;
	/**
	 * コメントユーザーのId
	 */
	private Integer userId;

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
		return "CommentForm [content=" + content + ", articleId=" + articleId + ", userId=" + userId + "]";
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
