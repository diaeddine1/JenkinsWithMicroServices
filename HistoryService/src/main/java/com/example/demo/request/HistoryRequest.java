package com.example.demo.request;

public class HistoryRequest {
	private int userId;

	public HistoryRequest() {

	}

	public HistoryRequest(int userId) {
		this.userId = userId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
