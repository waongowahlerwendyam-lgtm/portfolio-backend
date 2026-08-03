package com.waongo.portfolio_backend.service;

import com.waongo.portfolio_backend.dto.ContactRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.*;

@Service
public class EmailService {

    @Value("${brevo.api.key}")
    private String apiKey;

    public void envoyerEmailContact(ContactRequest request) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            String url = "https://api.brevo.com/v3/smtp/email";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("api-key", apiKey);

            Map<String, Object> requestBody = new HashMap<>();

            Map<String, String> sender = new HashMap<>();
            sender.put("email", "waongowahlerwendyam@gmail.com");
            sender.put("name", "Mon Portfolio");
            requestBody.put("sender", sender);

            List<Map<String, String>> to = new ArrayList<>();
            Map<String, String> recipient = new HashMap<>();
            recipient.put("email", "waongowahlerwendyam@gmail.com");
            to.add(recipient);
            requestBody.put("to", to);

            requestBody.put("subject", "Nouveau message de " + request.getNom());
            requestBody.put("textContent", "De : " + request.getEmail() + "\n\nMessage :\n" + request.getMessage());

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Erreur Brevo: " + response.getBody());
            }

        } catch (Exception e) {
            throw new RuntimeException("Erreur d'envoi: " + e.getMessage());
        }
    }
}