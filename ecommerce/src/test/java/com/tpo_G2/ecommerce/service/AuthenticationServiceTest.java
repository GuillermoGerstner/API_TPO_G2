package com.tpo_G2.ecommerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tpo_G2.ecommerce.dto.LoginRequestDTO;
import com.tpo_G2.ecommerce.dto.RegisterRequestDTO;
import com.tpo_G2.ecommerce.model.Role;
import com.tpo_G2.ecommerce.model.Usuario;
import com.tpo_G2.ecommerce.repository.UsuarioRepository;
import com.tpo_G2.ecommerce.security.JwtUtil;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {
    
    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthenticationService authenticationService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setIdUsuario(1L);
        usuario.setUsername("uade");
        usuario.setEmail("uade@test.com");
        usuario.setPassword("passwordEncriptada");
        usuario.setNombre("Uade");
        usuario.setApellido("Test");
        usuario.setRole(Role.USER);
    }

     @Test
    void register_DeberiaRegistrarUsuarioYDevolverToken() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setUsername("uade");
        request.setEmail("uade@test.com");
        request.setPassword("123456");
        request.setNombre("Uade");
        request.setApellido("Test");

        when(usuarioRepository.existsByEmail("uade@test.com")).thenReturn(false);
        when(passwordEncoder.encode("123456")).thenReturn("passwordEncriptada");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        String resultado = authenticationService.register(request);

        assertNotNull(resultado);
        assertEquals("Usuario registrado exitosamente", resultado);

        verify(usuarioRepository, times(1)).existsByEmail("uade@test.com");
        verify(passwordEncoder, times(1)).encode("123456");
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void authenticate_DeberiaAutenticarUsuarioYDevolverToken() {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail("uade@test.com");
        request.setPassword("123456");

        when(usuarioRepository.findByEmail("uade@test.com")).thenReturn(Optional.of(usuario));
        when(jwtUtil.generateToken(anyString(), anySet())).thenReturn("token-test");

        String resultado = authenticationService.authenticate(request);

        assertNotNull(resultado);
        assertEquals("token-test", resultado);

        verify(authenticationManager, times(1)).authenticate(any());
        verify(usuarioRepository, times(1)).findByEmail("uade@test.com");
        verify(jwtUtil, times(1)).generateToken(anyString(), anySet());
    }
}
