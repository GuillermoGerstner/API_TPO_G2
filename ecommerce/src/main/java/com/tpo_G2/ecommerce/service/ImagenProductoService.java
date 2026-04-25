package com.tpo_G2.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpo_G2.ecommerce.model.ImagenProducto;
import com.tpo_G2.ecommerce.repository.ImagenProductoRepository;

@Service
public class ImagenProductoService {
    @Autowired private ImagenProductoRepository imagenProductoRepository;

    public ImagenProducto crear(ImagenProducto img) {
        return imagenProductoRepository.save(img);
    }

    public List<ImagenProducto> listarPorProducto(Long idProducto) {
        return imagenProductoRepository.findByProductoId(idProducto);
    }
}
