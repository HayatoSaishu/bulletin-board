package com.example.domain;

/**
 * スレッドの情報.
 * 
 * @author h3sbs
 *
 */
public class Group {

	/**
	 * GROUPID
	 */
	private Integer id;
	/**
	 * 名前
	 */
	private String name;
	/**
	 * グループ概要
	 */
	private String overview;
	
	public Group() {
	}
	
	
	
	public Group(Integer id, String name, String overview) {
		super();
		this.id = id;
		this.name = name;
		this.overview = overview;
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
	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + ", overview=" + overview + "]";
	}
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
}
