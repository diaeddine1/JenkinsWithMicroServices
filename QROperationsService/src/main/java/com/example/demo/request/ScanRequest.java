package com.example.demo.request;

public class ScanRequest {
	private int id;
	private String data;

	public ScanRequest(int id, String data) {
		this.id = id;
		this.data = data;
	}

	public ScanRequest() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}