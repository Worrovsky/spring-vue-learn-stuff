package com.example.reset;

import com.example.registration.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PasswordResetToken {

    private static final int EXPIRATION_IN_MINUTES = 1;

    @Id
    @GeneratedValue
    private Long id;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User user;

    private LocalDateTime expiredTime;

    public PasswordResetToken() {
    }

    public PasswordResetToken(String token, User user) {
        this.token = token;
        this.user = user;

        this.expiredTime = calculateExpirationTime();
    }

    private LocalDateTime calculateExpirationTime() {
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

    public LocalDateTime getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(LocalDateTime expiredTime) {
        this.expiredTime = expiredTime;
    }
}
