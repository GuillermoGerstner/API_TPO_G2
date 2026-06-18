package com.tpo_G2.ecommerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateProductoDTO {
    
    @NotBlank
    private String nombre;

    private String descripcion;
    
    @NotNull
    @Min(0)
    private Float precio;

    @NotNull
    @Min(0)
    private Integer stock;

    //@NotNull
    private Long idUsuario;

    @NotNull
    private Long idCategoria;
}
