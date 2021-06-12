package com.example.verification;

import com.example.registration.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class VerificationToken {

    private static final long EXPIRATION_IN_MINUTES = 1;
    @Id
    @GeneratedValue
    private Long id;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User user;

    private LocalDateTime expirationDate;

    public VerificationToken() {
    }

    public VerificationToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expirationDate = calculateExpiration();
    }

    private LocalDateTime calculateExpiration() {
        return LocalDateTime.now().plusMinutes(EXPIRATION_IN_MINUTES);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

}
