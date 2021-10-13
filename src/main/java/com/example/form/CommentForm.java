package com.example.form;

public class CommentForm {

	/**
	 * コメント内容
	 */
	private String content;
	/**
	 * スレッドID
	 */
	private Integer articleId;
	/**
	 * コメントユーザーの名前
	 */
	private String userName;

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
		return "CommentForm [content=" + content + ", articleId=" + articleId + ", userName=" + userName + "]";
	}
}
