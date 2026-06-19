package com.tpo_G2.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tpo_G2.ecommerce.dto.CarritoDTO;
import com.tpo_G2.ecommerce.dto.PedidoDTO;
import com.tpo_G2.ecommerce.service.CarritoService;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @GetMapping("/actual")
    public CarritoDTO getCarritoActual(Authentication authentication) {
        return carritoService.getOrCreateCarritoByEmail(authentication.getName());
    }

    @PostMapping
    public ResponseEntity<CarritoDTO> createCarrito(@RequestParam Long usuarioId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carritoService.createCarrito(usuarioId));
    }

    @PostMapping("/{id}/agregar")
    public CarritoDTO addCarrito(@PathVariable Long id, @RequestParam Long productoId, @RequestParam int cantidad) {
        return carritoService.addProducto(id, productoId, cantidad);
    }

    @GetMapping("/{id}")
    public CarritoDTO getCarritoById(@PathVariable Long id) {
        return carritoService.getCarritoById(id);
    }

    @DeleteMapping("/{carritoId}/item/{itemId}")
    public CarritoDTO deleteItem(@PathVariable Long carritoId, @PathVariable Long itemId) {
        return carritoService.deleteItem(carritoId, itemId);
    }

    @DeleteMapping("/{carritoId}/vaciar")
    public CarritoDTO emptyCarrito(@PathVariable Long carritoId) {
        return carritoService.emptyCarrito(carritoId);
    }

    @PostMapping("/{carritoId}/checkout")
    public PedidoDTO checkout(@PathVariable Long carritoId) {
        return carritoService.checkout(carritoId);
    }
}