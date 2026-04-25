package com.tpo_G2.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tpo_G2.ecommerce.model.Role;

import com.tpo_G2.ecommerce.repository.UsuarioRepository;

import com.tpo_G2.ecommerce.security.JwtFilter;


import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final JwtFilter jwtFilter;

    private final UsuarioRepository usuarioRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado")); //Agregar GlobalException
    }

    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Reglas de seguridad
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas que no requieren autenticación
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/productos/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/categorias/**").permitAll() 

                        // Rutas que requieren autenticación para modificar productos

                        // Rutas exclusivas para administradores
                        //verifica que el usuario esté autenticado y tenga el rol ADMIN
                        .requestMatchers("/api/admin/**").hasRole(Role.ADMIN.name())
                        //RUTAS PARA ADMIN
                        .requestMatchers(HttpMethod.POST, "/api/usuarios").hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/api/usuarios/**").hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").hasRole(Role.ADMIN.name())


                        // RUTAS PARA ADMIN Y SELLER (Gestión de stock)
                        .requestMatchers(HttpMethod.POST, "/api/productos").hasAnyRole("ADMIN", "SELLER")
                        .requestMatchers(HttpMethod.PUT, "/api/productos/**").hasAnyRole("ADMIN", "SELLER")
                        .requestMatchers(HttpMethod.DELETE, "/api/productos/**").hasAnyRole("ADMIN", "SELLER")
                        .requestMatchers("/api/imagenes-productos/**").hasAnyRole("ADMIN", "SELLER")
                    
                        //RUTAS PARA USUARIOS REGISTRADOS 
                        .requestMatchers(HttpMethod.POST, "/api/categorias").authenticated() 
                        .requestMatchers(HttpMethod.PUT, "/api/categorias/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/categorias/**").authenticated()
                        .requestMatchers("/api/pedidos/**").authenticated()
                        .requestMatchers("/api/carrito/**").authenticated()
                        .requestMatchers("/api/direcciones/**").authenticated()

                        .anyRequest().authenticated())
                    
                                               
                        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
