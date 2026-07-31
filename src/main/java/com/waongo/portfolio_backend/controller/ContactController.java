package com.waongo.portfolio_backend.controller;

import com.waongo.portfolio_backend.dto.ContactRequest;
import com.waongo.portfolio_backend.service.EmailService;  // ← EmailService (pas Brevo)
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "https://portfolio-wahler.vercel.app"})
public class ContactController {

    private final EmailService emailService;  // ← EmailService

    @PostMapping("/contact")
    public ResponseEntity<String> recevoirMessage(@Valid @RequestBody ContactRequest request) {
        try {
            emailService.envoyerEmailContact(request);
            return ResponseEntity.ok("Message envoyé avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Erreur : " + e.getMessage());
        }
    }
}