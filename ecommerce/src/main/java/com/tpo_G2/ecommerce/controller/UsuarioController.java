package com.tpo_G2.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tpo_G2.ecommerce.model.Usuario;
import com.tpo_G2.ecommerce.service.UsuarioService;
import com.tpo_G2.ecommerce.dto.UsuarioDTO;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    //MAL
/*  @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }*/

    //Corrección en base a los comentarios del profesor
    @GetMapping
    public List<UsuarioDTO> getAllUsuarios() {
        return usuarioService.getAllUsuarios();  // DTO sin password
    }
    
    @GetMapping("/{idUsuario}")
    public UsuarioDTO getUsuarioById(@PathVariable Long idUsuario) {
        return usuarioService.getUsuarioById(idUsuario);
    }

    @PostMapping
    public UsuarioDTO addUsuario(@RequestBody Usuario usuario) {
        return usuarioService.addUsuario(usuario);
    }

    @PutMapping("/{idUsuario}")
    public UsuarioDTO updateUsuario(@PathVariable Long idUsuario, @RequestBody Usuario usuario) {
        return usuarioService.updateUsuario(idUsuario, usuario);
    }

    @DeleteMapping("/{idUsuario}")
    public UsuarioDTO deleteUsuarioById(@PathVariable Long idUsuario) {
        return usuarioService.deleteUsuarioById(idUsuario);
    }
}
