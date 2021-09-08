package com.example.domain;

/**
 * ユーザー情報
 * 
 * @author h3sbs
 *
 */
public class User {

	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 名前
	 */
	private String name;
	/**
	 * メールアドレス
	 */
	private String mailAddress;
	/**
	 * パスワード
	 */
	private String password;
	/**
	 * 記事ID
	 */
	private Integer articleId;
	/**
	 * コメントID
	 */
	private Integer commentId;

	public User() {
	}

	public User(Integer id, String name, String mailAddress, String password, Integer articleId, Integer commentId) {
		super();
		this.id = id;
		this.name = name;
		this.mailAddress = mailAddress;
		this.password = password;
		this.articleId = articleId;
		this.commentId = commentId;
	}




	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", mailAddress=" + mailAddress + ", password=" + password
				+ ", articleId=" + articleId + ", commentId=" + commentId + "]";
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
}
