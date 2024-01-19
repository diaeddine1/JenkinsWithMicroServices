package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.LoginRequest;
import com.example.demo.request.SignupRequest;
import com.example.demo.request.UpdatePassword;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class IdentityServiceTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		userRepository.deleteAll();
	}

	@Test
	void testSignup() throws Exception {
		SignupRequest signupRequest = new SignupRequest("testuser", "test@example.com", "password");

		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/users/signup")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(signupRequest)));

		result.andExpect(status().isCreated());

	}

	@Test
	void testLogin() throws Exception {

		User user = new User("testuser", "test@example.com", "password");
		userRepository.save(user);

		LoginRequest loginRequest = new LoginRequest("test@example.com", "password");

		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(loginRequest)));

		result.andExpect(status().isOk());

	}

	@Test
	void testUpdatePassword() throws Exception {
		User user = new User("testuser", "test@example.com", "password");
		userRepository.save(user);

		UpdatePassword updatePassword = new UpdatePassword(user.getId(), "password", "newpassword");

		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/users/updatepassword")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updatePassword)));

		result.andExpect(status().isOk());

	}

}
