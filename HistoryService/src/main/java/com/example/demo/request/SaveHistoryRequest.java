package com.example.demo.request;

public class SaveHistoryRequest {
	private int qrCodeId;
	private int userId;
	private String action;

	public SaveHistoryRequest() {

	}

	public SaveHistoryRequest(int qrCodeId, int userId, String action) {
		this.qrCodeId = qrCodeId;
		this.userId = userId;
		this.action = action;
	}

	public int getQrCodeId() {
		return qrCodeId;
	}

	public void setQrCodeId(int qrCodeId) {
		this.qrCodeId = qrCodeId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}
