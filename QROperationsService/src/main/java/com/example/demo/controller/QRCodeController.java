package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.example.demo.entity.QRCode;
import com.example.demo.repository.QRCodeRepository;
import com.example.demo.request.ScanRequest;
import com.example.demo.request.GenerateRequest;
import com.example.demo.request.QrRequest;
import com.example.demo.request.SaveHistoryRequest;

@CrossOrigin
@RestController
@RequestMapping("/qrcodes")
public class QRCodeController {
	@Autowired
	private QRCodeRepository qrCodeRepository;

	private RestTemplate restTemplate = new RestTemplate();

	@PostMapping("scan")
	public void scan(@RequestBody ScanRequest request) {
		int id = request.getId();
		String data = request.getData();
		String action = "Scanned";

		QRCode qrCode = qrCodeRepository.save(new QRCode(data));

		// Make HTTP POST request to save history
		SaveHistoryRequest SavehistoryRequest = new SaveHistoryRequest(qrCode.getId(), id, action);
		restTemplate.postForLocation("http://192.168.11.110:9776/history/save", SavehistoryRequest);
	}

	@PostMapping("generate")
	public void generate(@RequestBody GenerateRequest request) {
		int id = request.getId();
		String data = request.getData();
		String action = "Generated";

		QRCode qrCode = qrCodeRepository.save(new QRCode(data));

		// Make HTTP POST request to save history
		SaveHistoryRequest SavehistoryRequest = new SaveHistoryRequest(qrCode.getId(), id, action);
		restTemplate.postForLocation("http://192.168.11.110:9776/history/save", SavehistoryRequest);
	}

	@PostMapping("get")
	public ResponseEntity<QRCode> getQRCode(@RequestBody QrRequest request) {
		Optional<QRCode> optionalQRCode = qrCodeRepository.findById(request.getQrId());
		if (optionalQRCode.isPresent()) {
			QRCode qrCode = optionalQRCode.get();
			return ResponseEntity.ok(qrCode);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
