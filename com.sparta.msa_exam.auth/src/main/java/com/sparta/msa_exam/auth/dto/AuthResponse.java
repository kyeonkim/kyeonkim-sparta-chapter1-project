package com.sparta.msa_exam.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private String accessToken;

    public static AuthResponse of(String accessToken) {
        return AuthResponse.builder().accessToken(accessToken).build();
    }
}
