package com.tpo_G2.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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

                    /*   SACAR LO COMENTADO para activar el sistema de autenticacion
                        // Rutas que requieren autenticación para modificar productos

                        .requestMatchers(HttpMethod.POST, "/api/productos").authenticated() 
                        .requestMatchers(HttpMethod.PUT, "/api/productos/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/productos/**").authenticated()

                        // Rutas exclusivas para administradores
                        //verifica que el usuario esté autenticado y tenga el rol ADMIN
                        .requestMatchers("/api/admin/**").hasRole(Role.ADMIN.name())

                        .requestMatchers(HttpMethod.POST, "/api/categorias").authenticated() 
                        .requestMatchers(HttpMethod.PUT, "/api/categorias/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/categorias/**").authenticated()

                        .requestMatchers("/api/pedidos/**").authenticated()
                        .requestMatchers("/api/carrito/**").authenticated()

                        .anyRequest().authenticated())
                    */
                       

                        .anyRequest().permitAll()) // Esta linea permite usar cualquier endpoint sin autenticacion, comentarla si se activa la autenticacion
                        
                        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
