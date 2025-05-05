package com.worktime.tracker.controller;

import com.worktime.tracker.dto.JwtResponse;
import com.worktime.tracker.dto.LoginRequest;
import com.worktime.tracker.dto.SignupRequest;
import com.worktime.tracker.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(
            @Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticate(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(
            @Valid @RequestBody SignupRequest signupRequest) {
        return ResponseEntity.ok(authService.register(signupRequest));
    }
}
