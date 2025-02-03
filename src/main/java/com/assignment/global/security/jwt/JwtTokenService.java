package com.assignment.global.security.jwt;

import com.assignment.global.security.dto.UserInfo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenService {
    @Value("${jwt.access-token.secret-key}")
    private String secretKey;
    @Value("${jwt.access-token.expiration}")
    private Long expiration;
    @Value("${jwt.access-token.issuer}")
    private String issuer;

    public String generateAccessToken(String userId, String role) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiration);

        return Jwts
                .builder()
                .setIssuer(issuer)
                .setSubject(userId)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(getJwtTokenKey(secretKey))
                .compact();
    }

    public UserInfo parseAccessToken(String token) {
        try {
            Jws<Claims> claims =
                    Jwts.parserBuilder()
                            .setSigningKey(getJwtTokenKey(secretKey))
                            .requireIssuer(issuer)
                            .build()
                            .parseClaimsJws(token);
            String userId = String.valueOf(claims.getBody().getSubject());
            String role = (String) claims.getBody().get("role");

            return new UserInfo(userId, role);
        } catch (MalformedJwtException e) {
            log.error("malformed token", e);
            throw new RuntimeException(e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("jwt token not found", e);
            throw new RuntimeException(e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("jwt expired", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    private SecretKey getJwtTokenKey(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }


}
