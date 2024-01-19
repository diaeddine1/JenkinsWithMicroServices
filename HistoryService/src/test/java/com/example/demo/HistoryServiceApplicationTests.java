package com.example.demo;

import com.example.demo.entity.History;
import com.example.demo.repository.HistoryRepository;
import com.example.demo.request.HistoryRequest;
import com.example.demo.request.QrCodeRequest;
import com.example.demo.request.SaveHistoryRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HistoryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HistoryRepository historyRepository;

    @BeforeEach
    void setUp() {
        // Initialize mock data or reset database before each test
        historyRepository.deleteAll();
    }

    @Test
    void testGetHistory() throws Exception {
        // Mock data
        int userId = 1;
        History history = new History(1, userId, "action");
        when(historyRepository.findAll()).thenReturn(Arrays.asList(history));

        // Perform GET request
        ResultActions result = mockMvc.perform(
                post("/history/getall")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new HistoryRequest(userId)))
        );

        // Verify response
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(history.getId()))
                .andExpect(jsonPath("$[0].timestamp").isNotEmpty())
                .andExpect(jsonPath("$[0].action").value(history.getAction()))
                .andExpect(jsonPath("$[0].qrCode").isEmpty()); 
    }

    @Test
    void testSaveHistory() throws Exception {
        // Mock data
        SaveHistoryRequest saveHistoryRequest = new SaveHistoryRequest(1, 1, "action");

        // Perform POST request
        ResultActions result = mockMvc.perform(
                post("/history/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saveHistoryRequest))
        );

        // Verify response
        result.andExpect(status().isOk());

        // Verify that the history was saved
        List<History> histories = historyRepository.findAll();
        assert histories.size() == 1;
        assert histories.get(0).getUserId() == saveHistoryRequest.getUserId();
        assert histories.get(0).getAction().equals(saveHistoryRequest.getAction());
    }
    
}
