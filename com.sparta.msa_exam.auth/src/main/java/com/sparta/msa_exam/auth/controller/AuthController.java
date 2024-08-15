package com.sparta.msa_exam.auth.controller;

import com.sparta.msa_exam.auth.dto.AuthResponse;
import com.sparta.msa_exam.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signIn")
    public ResponseEntity<?> createAuthenticationToken(@RequestParam("user_id") String userId) {
        String token = authService.signIn(userId);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
