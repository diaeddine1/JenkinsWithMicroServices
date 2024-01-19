package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.example.demo.entity.History;
import com.example.demo.repository.HistoryRepository;
import com.example.demo.request.HistoryRequest;
import com.example.demo.request.HistoryRespond;
import com.example.demo.request.QrCodeRequest;
import com.example.demo.request.QrCodeRespond;
import com.example.demo.request.SaveHistoryRequest;

@CrossOrigin
@RestController
@RequestMapping("/history")
public class HistoryController {
	@Autowired
	private HistoryRepository historyRepository;

	private RestTemplate restTemplate = new RestTemplate();

	@PostMapping("/getall")
	public List<HistoryRespond> getHistory(@RequestBody HistoryRequest request) {
		List<HistoryRespond> userHistories = new ArrayList<>();
		List<History> allHistories = historyRepository.findAll();

		for (History history : allHistories) {
			if (history.getUserId() == request.getUserId()) {
				// Make a POST request to another endpoint to get QR code information
				String qrCodeEndpoint = "http://192.168.11.110:9776/qrcodes/get";
				ResponseEntity<QrCodeRespond> qrCodeResponse = restTemplate.postForEntity(qrCodeEndpoint,
						new QrCodeRequest(history.getQrCodeId()), QrCodeRespond.class);

				if (qrCodeResponse.getStatusCode().is2xxSuccessful()) {
					// Create HistoryRespond and add it to the list
					QrCodeRespond qrCodeRespond = qrCodeResponse.getBody();
					HistoryRespond historyRespond = new HistoryRespond(history.getId(), history.getTimestamp(),
							history.getAction(), qrCodeRespond);
					userHistories.add(historyRespond);
				}
			}
		}

		return userHistories;
	}

	@PostMapping("/save")
	public void saveHistory(@RequestBody SaveHistoryRequest request) {
		historyRepository.save(new History(request.getQrCodeId(), request.getUserId(), request.getAction()));
	}
}
