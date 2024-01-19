package com.example.demo.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class History {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String timestamp;
	private String action;
	private int qrCodeId; 
	private int userId; 

	public History() {
	}

	public History(int qrCodeId, int userId, String action) {
		this.timestamp = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		this.qrCodeId = qrCodeId;
		this.userId = userId;
		this.action = action;
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

}
