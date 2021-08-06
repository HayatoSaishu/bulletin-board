package com.example.domain;

/**
 * ユーザーが投稿した内容を保存する.
 * 
 * @author h3sbs
 *
 */
public class Post {
	
	/**
	 * 投稿ID
	 */
	private Integer id;
	/**
	 * 投稿内容(文字)
	 */
	private String comment;
	/**
	 * 投稿内容(画像)
	 */
	private String image;
	/**
	 * 投稿された掲示板のスレッドID
	 */
	private Integer groupId;
	
	public Post() {
	}
	
	
	public Post(Integer id, String comment, String image, Integer groupId) {
		super();
		this.id = id;
		this.comment = comment;
		this.image = image;
		this.groupId = groupId;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	@Override
	public String toString() {
		return "Post [id=" + id + ", comment=" + comment + ", image=" + image + ", groupId=" + groupId + "]";
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
}
