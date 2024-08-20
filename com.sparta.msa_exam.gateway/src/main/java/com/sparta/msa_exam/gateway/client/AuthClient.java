package com.sparta.msa_exam.gateway.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service")
public interface AuthClient {
    @GetMapping("/auth/verify") // 유저 검증 API
    Boolean verifyUser(@RequestParam(value = "user_id") String userId);
}
