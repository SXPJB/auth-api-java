package com.fsociety.authapi.unit.app.login;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fsociety.authapi.app.login.impl.JwtUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import org.joda.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

class JwtUtilsTests {

  @InjectMocks private JwtUtils jwtUtils;
  private final String SECRET = "otZ2r04c/M0dM0/9LvZB6seu5HGq5N2OJD1BnP1Sa0w=";
  private final long EXPIRATION = 72800L;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    ReflectionTestUtils.setField(jwtUtils, "SECRET_KEY", SECRET);
    ReflectionTestUtils.setField(jwtUtils, "EXPIRATION", EXPIRATION);
  }

  @Test
  void generateToken() {
    String user = "user";
    Map<String, Object> claims = Map.of("claim1", "claim1", "claim2", "claim2");

    SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes());
    String expectedToken =
        Jwts.builder()
            .subject(user)
            .claims(claims)
            .expiration(new Date(LocalDate.now().toDate().getTime() + EXPIRATION))
            .signWith(secretKey)
            .compact();

    String token = jwtUtils.generateToken(user, claims);
    assertEquals(expectedToken, token);
  }
}
