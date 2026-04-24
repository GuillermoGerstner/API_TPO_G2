package com.tpo_G2.ecommerce.dto;

import com.tpo_G2.ecommerce.model.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioDTO {
    private Long idUsuario;
    private String username;
    private String email;
    private String nombre;
    private String apellido;
    private Role role;
}
