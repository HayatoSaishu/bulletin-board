package com.example.domain;

import java.util.List;

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
	 * プロフィール
	 */
	private String profile;
	/**
	 * プロフィール写真のパス
	 */
	private String image;
	/**
	 * スレッドリスト
	 */
	private List<Article> articleList;
	/**
	 * コメントリスト
	 */
	private List<Comment> commentList;

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

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}


	public List<Article> getArticleList() {
		return articleList;
	}

	public void setArticleList(List<Article> articleList) {
		this.articleList = articleList;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}


	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", mailAddress=" + mailAddress + ", password=" + password
				+ ", profile=" + profile + ", image=" + image + ", articleList=" + articleList + ", commentList="
				+ commentList + "]";
	}
}
