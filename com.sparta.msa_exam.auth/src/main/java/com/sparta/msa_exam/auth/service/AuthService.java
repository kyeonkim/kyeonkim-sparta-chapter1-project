package com.sparta.msa_exam.auth.service;

import com.sparta.msa_exam.auth.domain.User;
import com.sparta.msa_exam.auth.domain.UserRepository;
import com.sparta.msa_exam.auth.dto.AuthRequest;
import com.sparta.msa_exam.auth.dto.AuthResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class AuthService {

    private final UserRepository userRepository;

    @Value("${spring.application.name}")
    private String issuer;

    @Value("${service.jwt.access-expiration}")
    private Long accessExpiration;

    private final SecretKey secretKey;

    public AuthService(UserRepository userRepository, @Value("${service.jwt.secret-key}") String secretKey) {
        this.userRepository = userRepository;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));;
    }

    public AuthResponse createAccessToken(String userId) {
        // 먼저, 회원이 가입되어 있는 회원인지 확인
        return userRepository.findByUserId(userId)
                .map(user -> AuthResponse.of(Jwts.builder()
                                .claim("user_id", user.getUserId())
                                .issuer(issuer)
                                .issuedAt(new Date(System.currentTimeMillis()))
                                .expiration(new Date(System.currentTimeMillis() + accessExpiration))
                                .signWith(secretKey, SignatureAlgorithm.HS512)
                                .compact())
                ).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                ); //유저가 존재하지 않는다면 Exception 을 발생
    }

    public void createUser(AuthRequest authRequest) {
        userRepository.save(User.createUser(authRequest.getUserId()));
    }
}
