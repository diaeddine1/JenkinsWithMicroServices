package com.example.demo.request;

public class HistoryRespond {
	private int id;
	private String timestamp;
	private String action;
	private QrCodeRespond qrCode;

	public HistoryRespond(int id, String timestamp, String action, QrCodeRespond qrCode) {
		this.id = id;
		this.timestamp = timestamp;
		this.action = action;
		this.qrCode = qrCode;
	}

	public HistoryRespond() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public QrCodeRespond getQrCode() {
		return qrCode;
	}

	public void setQrCode(QrCodeRespond qrCode) {
		this.qrCode = qrCode;
	}

}
