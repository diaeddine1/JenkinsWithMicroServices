package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.History;
import com.example.demo.repository.HistoryRepository;

@RestController
@RequestMapping("/history")
public class HistoryController {
	@Autowired
	private HistoryRepository historyRepository; 

	@GetMapping("/{id}")
	public History getHistoryById(@PathVariable int id) {
		return historyRepository.findById(id);
	}

	@GetMapping
	public List<History> getAllHistory() {
		return historyRepository.findAll();
	}

	@PostMapping
	public History createHistory(@RequestBody History history) {
		return historyRepository.save(history);
	}

	@PutMapping("/{id}")
	public History updateHistory(@PathVariable int id, @RequestBody History history) {
		history.setId(id);
		return historyRepository.save(history);
	}

	@DeleteMapping("/{id}")
	public void deleteHistory(@PathVariable int id) {
		historyRepository.deleteById(id);
	}
}
