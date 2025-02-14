package com.gigasma.hospital.controller;

import com.gigasma.hospital.dtos.LoginRequest;
import com.gigasma.hospital.dtos.LoginResponse;
import com.gigasma.hospital.dtos.SignUpRequest;
import com.gigasma.hospital.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController 
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        try {
            userService.signUp(signUpRequest);
            return ResponseEntity.ok("Registration successful! Please check your email to verify your account.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        try {
            userService.verifyEmail(token);
            return ResponseEntity.ok("Email verified successfully! You can now login.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

     @PostMapping("/login")
     public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
         try {
             LoginResponse response = userService.login(loginRequest);
             return ResponseEntity.ok(response);
         } catch (Exception e) {
             return ResponseEntity.badRequest().body(new LoginResponse(e.getMessage()));
         }
     }
     
}