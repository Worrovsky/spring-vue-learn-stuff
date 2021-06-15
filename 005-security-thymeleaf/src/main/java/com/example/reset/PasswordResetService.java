package com.example.reset;

import com.example.persistent.PasswordResetTokenRepository;
import com.example.registration.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    private static final Logger log = LoggerFactory.getLogger(PasswordResetService.class);

    @Autowired
    private PasswordResetTokenRepository repository;

    public PasswordResetToken createResetToken(User user) {
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(token, user);
        return repository.save(resetToken);
    }

    public void sendTokenToUser(HttpServletRequest req, PasswordResetToken token) {

        final String path = "/user/changePassword";
        DefaultUriBuilderFactory builderFactory = new DefaultUriBuilderFactory();
        URI resetUri = builderFactory.builder()
                .scheme(req.getScheme())
                .host(req.getServerName())
                .port(req.getServerPort())
                .path(path)
                .queryParam("id", token.getUser().getId())
                .queryParam("token", token.getToken())
                .build();

        log.info("-- link to reset: " + resetUri);
    }

    public PasswordResetToken getResetToken(String token) {
        return repository.findByToken(token);
    }

    public boolean isTokenExpired(PasswordResetToken token) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(token.getExpiredTime());
    }
}
