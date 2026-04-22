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

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }
    
    @GetMapping("/{idUsuario}")
    public Usuario getUsuarioById(@PathVariable Long idUsuario) {
        return usuarioService.getUsuarioById(idUsuario);
    }

    @PostMapping
    public Usuario addUsuario(@RequestBody Usuario usuario) {
        return usuarioService.addUsuario(usuario);
    }

    @PutMapping("/{idUsuario}")
    public Usuario updateUsuario(@PathVariable Long idUsuario, @RequestBody Usuario usuario) {
        return usuarioService.updateUsuario(idUsuario, usuario);
    }

    @DeleteMapping("/{idUsuario}")
    public Usuario deleteUsuarioById(@PathVariable Long idUsuario) {
        return usuarioService.deleteUsuarioById(idUsuario);
    }
}