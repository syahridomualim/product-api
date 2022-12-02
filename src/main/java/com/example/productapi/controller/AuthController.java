package com.example.productapi.controller;

import com.example.productapi.entity.User;
import com.example.productapi.model.user.AuthRegisterRequest;
import com.example.productapi.model.user.AuthRequest;
import com.example.productapi.model.user.AuthResponse;
import com.example.productapi.service.user.UserService;
import com.example.productapi.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping({"/auth"})
public class AuthController {


    private final AuthenticationManager authManager;
    private final JwtTokenUtil jwtUtil;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authManager, JwtTokenUtil jwtUtil, UserService userService) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword())
            );

            User user = (User) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(user);
            AuthResponse response = new AuthResponse(user.getEmail(), accessToken);

            return ResponseEntity.ok().body(response);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody AuthRegisterRequest authRegisterRequest) {
        User user = userService.register(authRegisterRequest);
        return ResponseEntity.ok().body(user);
    }
}
