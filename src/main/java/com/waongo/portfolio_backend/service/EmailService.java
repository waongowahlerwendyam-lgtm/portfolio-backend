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
            // 1. Créer le client HTTP
            RestTemplate restTemplate = new RestTemplate();

            // 2. URL de l'API Brevo
            String url = "https://api.brevo.com/v3/smtp/email";

            // 3. Configurer les headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("api-key", apiKey);

            // 4. Construire le corps de la requête
            Map<String, Object> requestBody = new HashMap<>();

            // Expéditeur
            Map<String, String> sender = new HashMap<>();
            sender.put("email", "noreply@brevo.com");
            sender.put("name", "Mon Portfolio");
            requestBody.put("sender", sender);

            // Destinataire (VOUS)
            List<Map<String, String>> to = new ArrayList<>();
            Map<String, String> recipient = new HashMap<>();
            recipient.put("email", "waongowahlerwendyam@gmail.com");
            to.add(recipient);
            requestBody.put("to", to);

            // Sujet
            requestBody.put("subject", "Nouveau message de " + request.getNom());

            // Contenu
            requestBody.put("textContent", "De : " + request.getEmail() + "\n\nMessage :\n" + request.getMessage());

            // 5. Assembler la requête
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            // 6. Envoyer
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            // 7. Vérifier
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Erreur Brevo: " + response.getBody());
            }

        } catch (Exception e) {
            throw new RuntimeException("Erreur d'envoi: " + e.getMessage());
        }
    }
}