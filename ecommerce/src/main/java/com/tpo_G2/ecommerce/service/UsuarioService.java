package com.tpo_G2.ecommerce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tpo_G2.ecommerce.dto.RegisterRequestDTO;
import com.tpo_G2.ecommerce.dto.UsuarioDTO;
import com.tpo_G2.ecommerce.exception.ResourceNotFoundException;
import com.tpo_G2.ecommerce.model.Role;
import com.tpo_G2.ecommerce.model.Usuario;
import com.tpo_G2.ecommerce.repository.UsuarioRepository;


import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder; 

   public List<UsuarioDTO> getAllUsuarios() {
    List<Usuario> usuarios = usuarioRepository.findAll();

    return usuarios.stream()
        .map(this::toUsuarioDTO)
        .collect(Collectors.toList());
    }

    public UsuarioDTO getUsuarioById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        return toUsuarioDTO(usuario);
    }


    public UsuarioDTO addUsuario(RegisterRequestDTO registroDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombre(registroDTO.getNombre());
        usuario.setApellido(registroDTO.getApellido());
        usuario.setUsername(registroDTO.getUsername());
        usuario.setEmail(registroDTO.getEmail());
        usuario.setRole(Role.USER);

        usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));

        return toUsuarioDTO(usuarioRepository.save(usuario));
    }


    public UsuarioDTO updateUsuario(Long id, RegisterRequestDTO usuarioDetalles) {
    Usuario usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));

    usuario.setUsername(usuarioDetalles.getUsername());
    usuario.setNombre(usuarioDetalles.getNombre());
    usuario.setApellido(usuarioDetalles.getApellido());
    usuario.setEmail(usuarioDetalles.getEmail());

    if (usuarioDetalles.getPassword() != null && !usuarioDetalles.getPassword().isBlank()) {
        usuario.setPassword(passwordEncoder.encode(usuarioDetalles.getPassword()));
    }

    return toUsuarioDTO(usuarioRepository.save(usuario));
}

    public UsuarioDTO deleteUsuarioById(Long id) {
    Usuario usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
    
    usuarioRepository.deleteById(id);
    
    return toUsuarioDTO(usuario);
}

    public void actualizarRol(Long id, Role nuevoRol) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        usuario.setRole(nuevoRol);
        usuarioRepository.save(usuario);
    }

    private UsuarioDTO toUsuarioDTO(Usuario usuario) {
    return new UsuarioDTO(
        usuario.getIdUsuario(),
        usuario.getUsername(),
        usuario.getEmail(),
        usuario.getNombre(),
        usuario.getApellido(),
        usuario.getRole()
    );
}

}
