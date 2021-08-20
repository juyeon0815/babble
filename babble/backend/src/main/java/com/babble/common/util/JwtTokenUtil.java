package com.babble.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;

import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.NoHandlerFoundException;

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

    public static boolean validateToken(String jwt) { // Jwt Token의 유효성을 체크
        return getClaims(jwt) != null;
    }

    public static Jws<Claims> getClaims(String jwt) {
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



    public static Date getTokenExpiration(int expirationTime) { //토큰 만료일자
        Date now = new Date();
        return new Date(now.getTime() + expirationTime);
    }

    public static void handleError(String token) {

        try {
            JWTVerifier verifier = JWT
                    .require(Algorithm.HMAC512(secretKey.getBytes()))
                    .withIssuer(ISSUER)
                    .build();

            DecodedJWT jwt = verifier.verify(token.replace(TOKEN_PREFIX, ""));

            System.out.println("=================== test_verifyJwtToken ===================");
            System.out.println("jwt token         : " + jwt.getToken());
            System.out.println("jwt algorithm     : " + jwt.getAlgorithm());
            System.out.println("jwt claims        : " + jwt.getClaims());
            System.out.println("jwt issuer        : " + jwt.getIssuer());
            System.out.println("jwt issuer date   : " + jwt.getIssuedAt());
            System.out.println("jwt expires date  : " + jwt.getExpiresAt());
            System.out.println("jwt signature     : " + jwt.getSignature());
            System.out.println("jwt type          : " + jwt.getType());
            System.out.println("jwt key id        : " + jwt.getKeyId());
            System.out.println("jwt id            : " + jwt.getId());
            System.out.println("jwt subject       : " + jwt.getSubject());
            System.out.println("jwt content type  : " + jwt.getContentType());
            System.out.println("jwt audience list : " + jwt.getAudience());

        } catch (AlgorithmMismatchException ex) {
            log.error("AlgorithmMismatchException");
            throw ex;
        } catch (InvalidClaimException ex) {
            log.error("InvalidClaimException");
            throw ex;
        } catch (SignatureGenerationException ex) {
            log.error("SignatureGenerationException");
            throw ex;
        } catch (SignatureVerificationException ex) {
            log.error("SignatureVerificationException");
            throw ex;
        } catch (TokenExpiredException ex) {
            log.error("TokenExpiredException");
            throw ex;
        } catch (JWTCreationException ex) {
            log.error("JWTCreationException");
            throw ex;
        } catch (JWTDecodeException ex) {
            log.error("JWTDecodeException");
            throw ex;
        } catch (JWTVerificationException ex) {
            log.error("JWTVerificationException");
            throw ex;
        } catch (Exception ex) {
            log.error("Exception");
            throw ex;
        }
    }
}
