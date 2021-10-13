package com.example.form;

import javax.validation.constraints.NotBlank;

/**
 * スレッド作成時のパラメータを受け取るフォーム.
 * 
 * @author hayato.saishu
 *
 */
public class ArticleForm {

	/**
	 * スレッド名
	 */
	@NotBlank
	private String name;
	/**
	 * スレッド内容
	 */
	@NotBlank
	private String content;
	
	/**
	 * スレッド作成者の名前
	 */
	private String userName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "ArticleForm [name=" + name + ", content=" + content + ", userName=" + userName + "]";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
