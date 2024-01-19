package com.example.demo;

import com.example.demo.entity.QRCode;
import com.example.demo.repository.QRCodeRepository;
import com.example.demo.request.GenerateRequest;
import com.example.demo.request.QrRequest;
import com.example.demo.request.SaveHistoryRequest;
import com.example.demo.request.ScanRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class QRCodeControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private QRCodeRepository qrCodeRepository;

	@BeforeEach
	void setUp() {
		// Initialize mock data or reset database before each test
		qrCodeRepository.deleteAll();
	}

	@Test
	void testGetQRCode() throws Exception {
		// Mock data
		QRCode qrCode = new QRCode("test-data");
		when(qrCodeRepository.findById(1)).thenReturn(Optional.of(qrCode));

		// Perform GET request
		ResultActions result = mockMvc.perform(post("/qrcodes/get").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new QrRequest(1))));

		// Verify response
		result.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data").value(qrCode.getData()));

	}

	@Test
	void testScan() throws Exception {
		// Mock data
		ScanRequest scanRequest = new ScanRequest(1, "scan-data");
		when(qrCodeRepository.save(Mockito.any(QRCode.class))).thenReturn(new QRCode("scan-data"));

		// Perform POST request
		ResultActions result = mockMvc.perform(post("/qrcodes/scan").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(scanRequest)));

		// Verify response
		result.andExpect(status().isOk());

		// Verify that the QRCode and history were saved
		List<QRCode> qrCodes = qrCodeRepository.findAll();
		assert qrCodes.size() == 1;

	}

	@Test
	void testGenerate() throws Exception {
		// Mock data
		GenerateRequest generateRequest = new GenerateRequest(1, "generate-data");
		when(qrCodeRepository.save(Mockito.any(QRCode.class))).thenReturn(new QRCode("generate-data"));

		// Perform POST request
		ResultActions result = mockMvc.perform(post("/qrcodes/generate").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(generateRequest)));

		// Verify response
		result.andExpect(status().isOk());

		// Verify that the QRCode and history were saved
		List<QRCode> qrCodes = qrCodeRepository.findAll();
		assert qrCodes.size() == 1;

	}

}
