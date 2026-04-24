package com.tpo_G2.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tpo_G2.ecommerce.model.ImagenProducto;
import com.tpo_G2.ecommerce.service.ImagenProductoService;

@RestController
@RequestMapping("/api/imagenes-productos")
public class ImagenProductoController {

    @Autowired
    private ImagenProductoService imagenService;

    @PostMapping
    public ResponseEntity<ImagenProducto> crear(@RequestBody ImagenProducto imagen) {
        return ResponseEntity.ok(imagenService.crear(imagen));
    }

    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<List<ImagenProducto>> listarPorProducto(@PathVariable Long id) {
        return ResponseEntity.ok(imagenService.listarPorProducto(id));
    }
}