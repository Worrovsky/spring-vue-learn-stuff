package com.example.verification;

import com.example.persistent.VerificationTokenRepository;
import com.example.registration.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class VerificationService {

    private final static Logger log = LoggerFactory.getLogger(VerificationService.class);

    @Autowired
    VerificationTokenRepository repository;

    public VerificationToken createToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, user);
        return repository.save(verificationToken);
    }

    public void sentTokenToUser(VerificationToken token) {
        // for simplicity just log url
        // in real app need send full link to email
        String confirmationUrl = "registrationConfirm?token=" + token.getToken();
        log.info("--- to confirm registration visit " + confirmationUrl);
    }

    public VerificationToken getToken(String token) {
        return repository.findByToken(token);
    }

    public boolean isTokenExpired(VerificationToken token) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(token.getExpirationDate());
    }
}
