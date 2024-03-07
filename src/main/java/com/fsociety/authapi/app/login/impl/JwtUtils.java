package com.fsociety.authapi.app.login.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

  @Value("${jwt.secret}")
  private String SECRET_KEY;

  @Value("${jwt.expiration}")
  private long EXPIRATION;

  public String generateToken(String user, Map<String, Object> claims) {
    return Jwts.builder()
        .subject(user)
        .claims(claims)
        .expiration(new Date(LocalDate.now().toDate().getTime() + EXPIRATION))
        .signWith(getSecretKey())
        .compact();
  }

  private SecretKey getSecretKey() {
    return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
  }
}
