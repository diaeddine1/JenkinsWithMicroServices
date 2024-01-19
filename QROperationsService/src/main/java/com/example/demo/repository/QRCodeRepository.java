package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.QRCode;

public interface QRCodeRepository extends JpaRepository<QRCode, Integer> {

	List<QRCode> findAll();

	Optional<QRCode> findById(int id);
}
