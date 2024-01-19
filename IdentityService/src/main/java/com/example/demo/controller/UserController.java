package com.example.demo.controller;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.LoginRequest;
import com.example.demo.request.SignupRequest;
import com.example.demo.request.UpdatePassword;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserRepository userRepository;

	// Endpoint for user signup
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
		// Check if the email is already in use
		if (userRepository.findByEmail(request.getEmail()) != null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is already in use");
		}

		// Create a new user and save it to the repository
		User newUser = new User(request.getUsername(), request.getEmail(), request.getPassword());
		userRepository.save(newUser);

		return ResponseEntity.status(HttpStatus.CREATED).body("Signup successful");
	}

	// Endpoint for user login
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody LoginRequest request) {
		// Find the user by email
		User user = userRepository.findByEmail(request.getEmail());

		// Check if the user exists and the password matches
		if (user != null && request.getPassword().equals(user.getPassword())) {
			return ResponseEntity.ok().body(user.getId());
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Collections.singletonMap("error", "Invalid credentials"));
		}
	}

	@PostMapping("/updatepassword")
	public String updatePassword(@RequestBody UpdatePassword updatePassword) {
	    try {
	        // Find the user by ID
	        Optional<User> optionalUser = userRepository.findById(updatePassword.getUserId());

	        if (optionalUser.isPresent()) {
	            User user = optionalUser.get();

	            // Check if the old password is correct
	            if (updatePassword.getOldpassword().equals(user.getPassword())) {
	                // Update the password and save the user
	                user.setPassword(updatePassword.getNewpassword());
	                userRepository.save(user);
	                return "Password updated successfully";
	            } else {
	                return "Old password is incorrect";
	            }
	        } else {
	            return "User not found";
	        }
	    } catch (Exception e) {
	        e.printStackTrace(); // Log the exception for debugging
	        return "An error occurred while updating the password";
	    }
	}

}
