package com.example.form;

/**
 * ユーザー登録を行うリクエストパラメータを受け取るフォーム.
 * 
 * @author h3sbs
 *
 */
public class SignUpUserForm {

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
		return "SignUpUserForm [name=" + name + ", mailAddress=" + mailAddress + ", password=" + password + "]";
	}

}
