package com.example.demo.service;

import com.example.demo.dto.auth.SignUpResponseDto;
import com.example.demo.dto.auth.SignUpRequestDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignUpResponseDto createUser(SignUpRequestDto signUpRequestDto) {
        String password = passwordEncoder.encode(signUpRequestDto.getPassword());
        User user = User.builder()
                .username(signUpRequestDto.getUsername())
                .password(password)
                .role(signUpRequestDto.getRole())
                .build();

        User saveduser = userRepository.save(user);

        return SignUpResponseDto.builder()
                .id(saveduser.getId())
                .username(saveduser.getUsername())
                .registeredAt(saveduser.getRegisteredAt())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username '%s' not found".formatted(username)));
    }
}
