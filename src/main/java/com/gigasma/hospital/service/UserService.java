package com.gigasma.hospital.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gigasma.hospital.dtos.LoginRequest;
import com.gigasma.hospital.dtos.LoginResponse;
import com.gigasma.hospital.dtos.SignUpRequest;
import com.gigasma.hospital.models.Doctor;
import com.gigasma.hospital.models.Patient;
import com.gigasma.hospital.models.User;
import com.gigasma.hospital.models.UserType;
import com.gigasma.hospital.repository.UserRepository;
import com.gigasma.hospital.config.JwtUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Transactional
    public void signUp(SignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user;
        if (request.getUserType() == UserType.DOCTOR) {
            user = new Doctor();
        } else {
            user = new Patient();
        }
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setType(request.getUserType());
        user.setEnabled(false);
        user.setVerificationToken(UUID.randomUUID().toString());

        userRepository.save(user);

        emailService.sendVerificationEmail(
                user.getEmail(),
                user.getName(),
                user.getVerificationToken());
    }

    @Transactional
    public void verifyEmail(String token) {
        User user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        user.setEnabled(true);
        user.setVerificationToken(null);
        userRepository.save(user);
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtUtil.generateToken(userDetails);

        return new LoginResponse(token);
    }
}
