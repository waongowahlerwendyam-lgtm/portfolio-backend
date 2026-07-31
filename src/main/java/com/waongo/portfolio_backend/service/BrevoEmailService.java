package com.waongo.portfolio_backend.service;

import com.waongo.portfolio_backend.dto.ContactRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sendinblue.ApiClient;
import sendinblue.ApiException;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibapi.TransactionalEmailsApi;
import sibmodel.SendSmtpEmail;
import sibmodel.SendSmtpEmailSender;
import sibmodel.SendSmtpEmailTo;

import java.util.Collections;

@Service
public class BrevoEmailService {

    @Value("${brevo.api.key}")
    private String apiKey;

    public void envoyerEmailContact(ContactRequest request) {
        try {
            // 1. Configurer le client avec votre clé API
            ApiClient client = Configuration.getDefaultApiClient();
            ApiKeyAuth apiKeyAuth = (ApiKeyAuth) client.getAuthentication("api-key");
            apiKeyAuth.setApiKey(apiKey);

            // 2. Préparer l'email
            TransactionalEmailsApi api = new TransactionalEmailsApi();

            SendSmtpEmail email = new SendSmtpEmail();

            // Expéditeur (DOIT être validé sur Brevo)
            email.setSender(new SendSmtpEmailSender()
                    .email("noreply@brevo.com")
                    .name("Mon Portfolio"));

            // Destinataire (VOUS)
            email.setTo(Collections.singletonList(
                    new SendSmtpEmailTo().email("waongowahlerwendyam@gmail.com")
            ));

            // Sujet
            email.setSubject("Nouveau message de " + request.getNom());

            // Contenu
            email.setTextContent(
                    "De : " + request.getEmail() + "\n\n" +
                            "Message :\n" + request.getMessage()
            );

            // 3. Envoyer
            api.sendTransacEmail(email);

        } catch (ApiException e) {
            throw new RuntimeException("Erreur d'envoi: " + e.getMessage());
        }
    }
}