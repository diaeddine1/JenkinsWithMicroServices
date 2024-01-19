package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
public class History {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String timestamp; // Change the data type to String
	private String action;
	@OneToOne
	private QRCode qrCode;

	@ManyToOne
	@JsonIgnore
	private User user;

	public History() {
	}

	public History(QRCode qrCode, User user, String action) {
		this.timestamp = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		this.qrCode = qrCode;
		this.user = user;
		this.action = action;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public QRCode getQrCode() {
		return qrCode;
	}

	public void setQrCode(QRCode qrCode) {
		this.qrCode = qrCode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
