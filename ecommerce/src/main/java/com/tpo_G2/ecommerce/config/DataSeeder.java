package com.tpo_G2.ecommerce.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tpo_G2.ecommerce.model.Categoria;
import com.tpo_G2.ecommerce.model.Role;
import com.tpo_G2.ecommerce.model.Usuario;
import com.tpo_G2.ecommerce.repository.CategoriaRepository;
import com.tpo_G2.ecommerce.repository.UsuarioRepository;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initAdmin(UsuarioRepository usuarioRepository, CategoriaRepository categoriaRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!usuarioRepository.existsByEmail("admin@ecommerce.com")) {
                //Creo un usuario admin si no existe ninguno en la base de datos
                Usuario admin = Usuario.builder()
                        .nombre("Administrador")
                        .apellido("Sistema")
                        .email("admin@ecommerce.com")
                        .username("admin")
                        .password(passwordEncoder.encode("Admin1234")) 
                        .role(Role.ADMIN)
                        .build();
                usuarioRepository.save(admin);
                System.out.println(" Administrador inicial creado.");
            }
            // Crear categorías iniciales si no existen
            crearCategoriaSiNoExiste(categoriaRepository, "Electrónica");
            crearCategoriaSiNoExiste(categoriaRepository, "Ropa");
            crearCategoriaSiNoExiste(categoriaRepository, "Hogar");
            crearCategoriaSiNoExiste(categoriaRepository, "Deportes");
            crearCategoriaSiNoExiste(categoriaRepository, "Libros");
        };
    }

    private void crearCategoriaSiNoExiste(CategoriaRepository categoriaRepository, String nombre) {
        if (!categoriaRepository.existsByNombreIgnoreCase(nombre)) {
            Categoria categoria = new Categoria();
            categoria.setNombre(nombre);
            categoriaRepository.save(categoria);
            System.out.println("Categoría inicial creada: " + nombre);
        }
    }
}
