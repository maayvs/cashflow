package com.mayara.cashflow.service;

import com.mayara.cashflow.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {
    private final JwtEncoder jwtEncoder;
    private final long expirationHours;

    public TokenService(JwtEncoder jwtEncoder, @Value("${jwt.expiration-hours}") long expirationHours) {
        this.jwtEncoder = jwtEncoder;
        this.expirationHours = expirationHours;
    }

    public String generateToken(User user) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("cashflow")
                .issuedAt(now)
                .expiresAt(now.plus(expirationHours, ChronoUnit.HOURS))
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("name", user.getName())
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }
}
