package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.History;
import com.example.demo.entity.LoginRequest;
import com.example.demo.entity.QRCode;
import com.example.demo.entity.SignupRequest;
import com.example.demo.entity.UpdatePassword;
import com.example.demo.entity.User;
import com.example.demo.repository.HistoryRepository;
import com.example.demo.repository.QRCodeRepository;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private QRCodeRepository qrCodeRepository ;
	
	@Autowired 
	private HistoryRepository historyRepository;

	@GetMapping("/all")
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	@GetMapping("/{id}")
	public User getUserById(@PathVariable int id) {
		return userRepository.findById(id);
	}

	@GetMapping
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@PostMapping
	public User createUser(@RequestBody User user) {
		return userRepository.save(user);
	}

	@PutMapping("/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User user) {
		user.setId(id);
		return userRepository.save(user);
	}

	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
	}

	@PostMapping("/signup")
	public String signup(@RequestBody SignupRequest request) {
		if (userRepository.findByEmail(request.getEmail()) != null) {
			return "Email is already in use";
		}

		User newUser = new User(request.getUsername(), request.getEmail(), request.getPassword());
		userRepository.save(newUser);

		return "Signup successful";
	}

	@PostMapping("/login")
	public int login(@RequestBody LoginRequest request) {
		User user = userRepository.findByEmail(request.getEmail());
		if (user != null && request.getPassword().equals(user.getPassword())) {
			return user.getId();
		} else {
			return 0;
		}
	}
	
	@GetMapping("history/{id}")
	public List<History> getHistory(@PathVariable int id) {
		return userRepository.findById(id).getHistories();

	}
	
	
	@GetMapping("scan/{id}/{data}")
	public int scan(@PathVariable int id, @PathVariable String data ) {
		String s ="Scanned";
		User user = userRepository.findById(id);
		QRCode qrCode = qrCodeRepository.save(new QRCode(data));
		historyRepository.save(new History(qrCode, user, s) );
		return 0 ;
	}	
	
	@GetMapping("generate/{id}/{data}")
	public int generate(@PathVariable int id, @PathVariable String data ) {
		String g ="Generated";
		User user = userRepository.findById(id);
		QRCode qrCode = qrCodeRepository.save(new QRCode(data));
		historyRepository.save(new History(qrCode, user, g) );
		return 0 ;
	}
	
	@PostMapping("/updatePassword")
	public String updatePassword(@RequestBody UpdatePassword updatePassword) {
		User user = userRepository.findById(updatePassword.getUserId());
		if (user != null && updatePassword.getOldpassword().equals(user.getPassword())) {
			user.setPassword(updatePassword.getNewpassword());
			userRepository.save(user);
			return "Password updated successfully";
		} else {
			return "Old password is incorrect";
		}
	}
}
