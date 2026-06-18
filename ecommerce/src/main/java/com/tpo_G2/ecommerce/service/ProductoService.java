package com.tpo_G2.ecommerce.service;

import java.util.List;
import java.util.stream.Collectors; // 👈 Importante para que no falle el .collect()

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpo_G2.ecommerce.dto.CategoriaDTO;
import com.tpo_G2.ecommerce.dto.CreateProductoDTO;
import com.tpo_G2.ecommerce.dto.ProductoDTO;
import com.tpo_G2.ecommerce.exception.BadRequestException;
import com.tpo_G2.ecommerce.exception.ResourceNotFoundException;
import com.tpo_G2.ecommerce.model.Categoria;
import com.tpo_G2.ecommerce.model.Producto;
import com.tpo_G2.ecommerce.model.Usuario;
import com.tpo_G2.ecommerce.repository.CategoriaRepository;
import com.tpo_G2.ecommerce.repository.ProductoRepository;
import com.tpo_G2.ecommerce.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // 1. Traer todos los productos (Catálogo general)
    public List<ProductoDTO> getAllProductos() {
        return productoRepository.findAll().stream()
            .map(this::toProductoDTO)
            .collect(Collectors.toList());
    }
    
    // 2. Buscar un producto por ID (para editarlo o ver detalles)
    public ProductoDTO getProductoById(Long id) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
        return toProductoDTO(producto);
    }

    // 3. Eliminar un producto por ID
    public ProductoDTO deleteProductoById(Long id) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
        productoRepository.deleteById(id);
        return toProductoDTO(producto);
    }

    // 4. Crear un Producto vinculándolo de forma segura mediante el Email del Token
    public ProductoDTO addProductoConEmail(CreateProductoDTO request, String email) {
        // 1. Validación de seguridad para atrapar hilos anónimos
        if (email == null || email.equals("anonymousUser") || email.isBlank()) {
            throw new BadRequestException("Sesión inválida o expirada. Por favor, vuelva a iniciar sesión.");
        }

        if (request.getPrecio() < 0 || request.getStock() < 0) {
            throw new BadRequestException("El precio o el stock no pueden ser negativos");
        }

        // 2. Buscamos el usuario por su email único
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un usuario registrado con el email: " + email));

        // 3. Buscamos la categoría
        Categoria categoria = categoriaRepository.findById(request.getIdCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + request.getIdCategoria()));

        // 4. Construimos el producto de forma segura
        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setUsuario(usuario);
        producto.setCategoria(categoria);

        return toProductoDTO(productoRepository.save(producto));
    }

    // 5. Modificar un Producto existente de forma segura mediante el Email del Token
    public ProductoDTO updateProductoConEmail(Long id, CreateProductoDTO request, String email) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
        
        if (request.getPrecio() < 0 || request.getStock() < 0) {
            throw new BadRequestException("Precio o stock inválidos");
        }

        Categoria categoria = categoriaRepository.findById(request.getIdCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + request.getIdCategoria()));

        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setCategoria(categoria);

        return toProductoDTO(productoRepository.save(producto));
    }

    // 6. Filtrar los productos para la grilla de Gestión usando el Email del creador
    public List<ProductoDTO> getProductosByUsuarioEmail(String email) {
        return productoRepository.findByUsuarioEmail(email).stream()
            .map(this::toProductoDTO)
            .collect(Collectors.toList());
    }

    // Mapeador interno de Entidad a DTO para evitar código duplicado
    private ProductoDTO toProductoDTO(Producto producto) {
        return new ProductoDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getCategoria() != null
                        ? new CategoriaDTO(producto.getCategoria().getId(), producto.getCategoria().getNombre())
                        : null
        );
    }
}