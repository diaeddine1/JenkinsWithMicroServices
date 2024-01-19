package com.example.demo.request;

public class UpdatePassword {

	private int UserId;
	private String oldpassword;
	private String newpassword;

	public UpdatePassword(int userId, String oldpassword, String newpassword) {
		UserId = userId;
		this.oldpassword = oldpassword;
		this.newpassword = newpassword;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public String getOldpassword() {
		return oldpassword;
	}

	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

}
