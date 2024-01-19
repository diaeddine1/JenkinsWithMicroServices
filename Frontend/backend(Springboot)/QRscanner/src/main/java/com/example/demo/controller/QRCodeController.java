package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.QRCode;
import com.example.demo.repository.QRCodeRepository;

@RestController
@RequestMapping("/qrcodes")
public class QRCodeController {
    @Autowired
    private QRCodeRepository qrCodeRepository; 

    @GetMapping("/{id}")
    public QRCode getQRCodeById(@PathVariable int id) {
        return qrCodeRepository.findById(id);
    }

    @GetMapping
    public List<QRCode> getAllQRCodes() {
        return qrCodeRepository.findAll();
    }

    @PostMapping
    public QRCode createQRCode(@RequestBody QRCode qrCode) {
        return qrCodeRepository.save(qrCode);
    }

    @PutMapping("/{id}")
    public QRCode updateQRCode(@PathVariable int id, @RequestBody QRCode qrCode) {
        qrCode.setId(id);
        return qrCodeRepository.save(qrCode);
    }

    @DeleteMapping("/{id}")
    public void deleteQRCode(@PathVariable int id) {
        qrCodeRepository.deleteById(id);
    }
}

