ackage com.tpo_G2.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tpo_G2.ecommerce.model.Carrito;
import com.tpo_G2.ecommerce.service.CarritoService;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {
    @Autowired
    private CarritoService carritoService;

    @PostMapping("/{id}/agregar")
    public Carrito addCarrito(@PathVariable Long id, @RequestParam Long productoId, @RequestParam int cantidad) {
        return carritoService.addProducto(id, productoId, cantidad);
    }
}
