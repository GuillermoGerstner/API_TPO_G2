package com.tpo_G2.ecommerce.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tpo_G2.ecommerce.dto.LoginRequestDTO;
import com.tpo_G2.ecommerce.dto.RegisterRequestDTO;
import com.tpo_G2.ecommerce.model.Role;
import com.tpo_G2.ecommerce.model.Usuario;
import com.tpo_G2.ecommerce.repository.UsuarioRepository;
import com.tpo_G2.ecommerce.security.JwtUtil;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    
    public String register(RegisterRequestDTO request) {

        
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            //Agregar GlobalException
            throw new RuntimeException("El Email ya exsiste");
        }

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        usuarioRepository.save(usuario);
        
        return "Usuario registrado exitosamente";
    }

    
    public String authenticate(LoginRequestDTO request) {
        
       
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        Usuario user = usuarioRepository.findByEmail(request.getEmail()).orElseThrow();
        
        
        Set<String> roles = user.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.toSet());
        return jwtUtil.generateToken(user.getEmail(), roles);
    }
}
