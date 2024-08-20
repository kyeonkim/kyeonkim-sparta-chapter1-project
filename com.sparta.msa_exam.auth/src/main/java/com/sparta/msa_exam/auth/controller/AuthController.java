package com.sparta.msa_exam.auth.controller;

import com.sparta.msa_exam.auth.dto.AuthRequest;
import com.sparta.msa_exam.auth.dto.AuthResponse;
import com.sparta.msa_exam.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final String serverPort;

    public AuthController(AuthService authService, @Value("19095") String serverPort) {
        this.authService = authService;
        this.serverPort = serverPort;
    }

    @GetMapping("/verify")
    public ResponseEntity<Boolean> verifyUser(@RequestParam("user_id") String userId) {
        Boolean response = authService.verifyUser(userId);
        return createResponse(ResponseEntity.ok(response));
    }

    @GetMapping("/signIn")
    public ResponseEntity<AuthResponse> createAuthenticationToken(@RequestParam("user_id") String userId) {
        AuthResponse response = authService.createAccessToken(userId);
        return createResponse(ResponseEntity.ok(response));
    }

    @PostMapping("/signUp")
    public ResponseEntity<Boolean> createUser(@RequestBody AuthRequest authRequest) {
        authService.createUser(authRequest);
        return createResponse(ResponseEntity.ok(true));
    }


    public <T> ResponseEntity<T> createResponse(ResponseEntity<T> response) {
        HttpHeaders headers = HttpHeaders.writableHttpHeaders(response.getHeaders());
        headers.set("Server-Port", serverPort);
        return new ResponseEntity<>(response.getBody(), headers, response.getStatusCode());
    }
}
