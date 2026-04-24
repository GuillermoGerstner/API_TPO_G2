package com.tpo_G2.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpo_G2.ecommerce.model.Direccion;
import com.tpo_G2.ecommerce.repository.DireccionRepository;

@Service
public class DireccionService {
    @Autowired private DireccionRepository direccionRepository;

    public List<Direccion> listarPorUsuario(Long idUsuario) {
        return direccionRepository.findByUsuarioIdUsuario(idUsuario);
    }

    public Direccion crear(Direccion d) {
        return direccionRepository.save(d);
    }

    public void eliminar(Long id) {
        direccionRepository.deleteById(id);
    }
    // Implementar update según necesidad de campos (calle, ciudad, etc)
}
