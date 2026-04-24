package com.tpo_G2.ecommerce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpo_G2.ecommerce.dto.CategoriaDTO;
import com.tpo_G2.ecommerce.exception.ResourceNotFoundException;
import com.tpo_G2.ecommerce.model.Categoria;
import com.tpo_G2.ecommerce.repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<CategoriaDTO> getAllCategorias() {
        return categoriaRepository.findAll().stream()
            .map(categoria -> new CategoriaDTO(categoria.getId(), categoria.getNombre()))
            .collect(Collectors.toList());
    }

    public Categoria saveCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public CategoriaDTO getCategoriaById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada con id: " + id));
        return new CategoriaDTO(categoria.getId(), categoria.getNombre());
    }

    public Categoria deleteCategoriaById(Long id){
        Categoria categoria = categoriaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada con id: " + id));
        categoriaRepository.deleteById(id);
        return categoria;
    }
}