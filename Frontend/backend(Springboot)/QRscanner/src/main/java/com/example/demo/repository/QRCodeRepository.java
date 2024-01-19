package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.QRCode;

public interface QRCodeRepository extends JpaRepository<QRCode, Integer> {

	List<QRCode> findAll();

	QRCode findById(int id);
}
