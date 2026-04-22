package com.tpo_G2.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpo_G2.ecommerce.model.Categoria;
import com.tpo_G2.ecommerce.repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> getAllCategorias() {
        return categoriaRepository.findAll();
    }

    public Categoria saveCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria getCategoriaById(Long id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    public Categoria deleteCategoriaById(Long id){
        Categoria categoria = categoriaRepository.findById(id).orElse(null);
        if(categoria != null){
            categoriaRepository.deleteById(id);
        }
        return categoria;
    }
}