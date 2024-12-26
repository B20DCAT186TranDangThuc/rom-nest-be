package com.dangthuc.newword.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtEncoder jwtEncoder;

    public static final String JWT_KEY = "L1r6G5HeX3zZ/Vb69HlDGeg57K9QKcg0IaLOdX7iIqX82kUhabJlwayKYpfhDxMsagYTzEaHkylkKLarxvKWhw==";
    public static final Long JWT_EXPIRE = 3600L;
    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;

    public static SecretKey getSecretKey() {
        byte[] keyBytes = Base64.getDecoder().decode(JWT_KEY);
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, JWT_ALGORITHM.getName());
    }

    public String generateToken(String username) {
        Instant now = Instant.now();
        Instant validity = now.plus(JWT_EXPIRE, ChronoUnit.SECONDS);

        List<String> listAuthority = new ArrayList<>();

        listAuthority.add("ROLE_USER_CREATE");
        listAuthority.add("ROLE_USER_UPDATE");

        // @formatter:off
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(username)
                .claim("permission", listAuthority)
                .build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }
}
