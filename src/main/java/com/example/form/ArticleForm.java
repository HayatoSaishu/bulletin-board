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
	 * スレッド作成者のID
	 */
	private Integer userId;

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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "ArticleForm [name=" + name + ", content=" + content + ", userId=" + userId + "]";
	}



}
