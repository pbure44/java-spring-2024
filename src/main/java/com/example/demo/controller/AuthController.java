package com.example.demo.controller;

import com.example.demo.dto.auth.AuthRequestDto;
import com.example.demo.dto.auth.AuthResponseDto;
import com.example.demo.dto.auth.SignUpRequestDto;
import com.example.demo.dto.auth.SignUpResponseDto;
import com.example.demo.service.JwtService;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {
        SignUpResponseDto signUpResponseDto = userService.createUser(signUpRequestDto);
        return ResponseEntity.ok(signUpResponseDto);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponseDto> signIn(@RequestBody @Valid AuthRequestDto authRequestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(), authRequestDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        if (authentication.isAuthenticated()) {
            UserDetails user = userService.loadUserByUsername(authRequestDto.getUsername());
            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);
            return ResponseEntity.ok(
                    AuthResponseDto.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .build()
            );
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
