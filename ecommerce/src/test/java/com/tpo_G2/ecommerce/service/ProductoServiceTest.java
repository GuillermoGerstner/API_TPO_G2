package com.tpo_G2.ecommerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tpo_G2.ecommerce.dto.CreateProductoDTO;
import com.tpo_G2.ecommerce.dto.ProductoDTO;
import com.tpo_G2.ecommerce.exception.ResourceNotFoundException;
import com.tpo_G2.ecommerce.model.Categoria;
import com.tpo_G2.ecommerce.model.Producto;
import com.tpo_G2.ecommerce.model.Usuario;
import com.tpo_G2.ecommerce.repository.CategoriaRepository;
import com.tpo_G2.ecommerce.repository.ProductoRepository;
import com.tpo_G2.ecommerce.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {
    
    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private ProductoService productoService;

    private Usuario usuario;
    private Categoria categoria;
    private Producto producto;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setIdUsuario(1L);
        usuario.setNombre("Admin");

        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Computacion");

        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Notebook i7");
        producto.setDescripcion("Notebook con procesador i7, 16GB RAM y 512GB SSD.");
        producto.setPrecio(720000F);
        producto.setStock(10);
        producto.setUsuario(usuario);
        producto.setCategoria(categoria);
    }

    @Test
    void addProducto_DeberiaCrearProducto() {
        CreateProductoDTO request = new CreateProductoDTO();
        request.setNombre("Notebook i7");
        request.setDescripcion("Notebook con procesador i7, 16GB RAM y 512GB SSD.");
        request.setPrecio(720000F);
        request.setStock(10);
        request.setIdUsuario(1L);
        request.setIdCategoria(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        ProductoDTO resultado = productoService.addProducto(request);

        assertNotNull(resultado);
        assertEquals("Notebook i7", resultado.getNombre());
        assertEquals(720000F, resultado.getPrecio());
        assertEquals(10, resultado.getStock());

        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    void getProductoById_CuandoNoExiste_DeberiaLanzarExcepcion() {
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            productoService.getProductoById(99L);
        });

        verify(productoRepository, times(1)).findById(99L);
    }
}
