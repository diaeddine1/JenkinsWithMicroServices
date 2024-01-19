package com.example.demo.request;

import com.example.demo.entity.History;

public class QrCodeRespond {

	private int id;
	private String data;

	public QrCodeRespond(int id, String data, History history) {
		this.id = id;
		this.data = data;
	}
	public QrCodeRespond(int id) {
		this.id = id;
		
	}


	public QrCodeRespond() {
	}

	public QrCodeRespond(String data) {

		this.data = data;
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
