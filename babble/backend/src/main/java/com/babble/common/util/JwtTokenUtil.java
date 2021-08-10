package com.babble.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;

import com.auth0.jwt.exceptions.InvalidClaimException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static com.google.common.collect.Lists.newArrayList;

/**
 * jwt 토큰 유틸 정의.
 * 유저 정보로 jwt 토큰을 만들거나 토큰을 바탕으로 유저 정보를 가져옴
 */
//@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenUtil {
    @Value("${jwt.secret}")
    private static String secretKey;
    private static Integer expirationTime;

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String ISSUER = "babble.com";

    @Autowired
    public JwtTokenUtil(@Value("${jwt.secret}") String secretKey, @Value("${jwt.expiration}") Integer expirationTime) {
        this.secretKey = secretKey;
        this.expirationTime = expirationTime;
    }

    // websocket 보안용 security
    /**
     * Jwt Token을 복호화 하여 이름을 얻는다.
     */
    public static String getUserNameFromJwt(String jwt) {
        return getClaims(jwt).getBody().getSubject();
    }

    /**
     * Jwt Token의 유효성을 체크한다.
     */
    public static boolean validateToken(String jwt) {
//        System.out.println(">>> getClaims(jwt )" + getClaims(jwt));
        return getClaims(jwt) != null;
    }

    private static Jws<Claims> getClaims(String jwt) {
        try {
            return Jwts.parser()
                .setSigningKey(secretKey.getBytes(Charset.forName("UTF-8"))).
                parseClaimsJws(jwt.replace("{", "")
                .replace("}",""));

        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
            throw ex;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
            throw ex;
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            throw ex;
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
            throw ex;
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
            throw ex;
        }
    }

    public void setExpirationTime() {
        //JwtTokenUtil.expirationTime = Integer.parseInt(expirationTime);
        JwtTokenUtil.expirationTime = expirationTime;
    }

    public static JWTVerifier getVerifier() {
        return JWT
                .require(Algorithm.HMAC512(secretKey.getBytes()))
                .withIssuer(ISSUER)
                .build();
    }

    public static String getToken(String email) { //토큰생성
        Date expires = JwtTokenUtil.getTokenExpiration(expirationTime);
        System.out.println(expires);
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(expires)
                .withIssuer(ISSUER)
                .withIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .sign(Algorithm.HMAC512(secretKey.getBytes()));
    }

    public static String getToken(Instant expires, String userId) {
        return JWT.create()
                .withSubject(userId)
                .withExpiresAt(Date.from(expires))
                .withIssuer(ISSUER)
                .withIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .sign(Algorithm.HMAC512(secretKey.getBytes()));
    }

    public static Date getTokenExpiration(int expirationTime) { //토큰 만료일자
        Date now = new Date();
        return new Date(now.getTime() + expirationTime);
    }

    public static void handleError(String token) {
        JWTVerifier verifier = JWT
                .require(Algorithm.HMAC512(secretKey.getBytes()))
                .withIssuer(ISSUER)
                .build();

        try {
            verifier.verify(token.replace(TOKEN_PREFIX, ""));
        } catch (AlgorithmMismatchException ex) {
            throw ex;
        } catch (InvalidClaimException ex) {
            throw ex;
        } catch (SignatureGenerationException ex) {
            throw ex;
        } catch (SignatureVerificationException ex) {
            throw ex;
        } catch (TokenExpiredException ex) {
            throw ex;
        } catch (JWTCreationException ex) {
            throw ex;
        } catch (JWTDecodeException ex) {
            throw ex;
        } catch (JWTVerificationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static void handleError(JWTVerifier verifier, String token) {
        try {
            verifier.verify(token.replace(TOKEN_PREFIX, ""));
        } catch (AlgorithmMismatchException ex) {
            throw ex;
        } catch (InvalidClaimException ex) {
            throw ex;
        } catch (SignatureGenerationException ex) {
            throw ex;
        } catch (SignatureVerificationException ex) {
            throw ex;
        } catch (TokenExpiredException ex) {
            throw ex;
        } catch (JWTCreationException ex) {
            throw ex;
        } catch (JWTDecodeException ex) {
            throw ex;
        } catch (JWTVerificationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
    }
}
