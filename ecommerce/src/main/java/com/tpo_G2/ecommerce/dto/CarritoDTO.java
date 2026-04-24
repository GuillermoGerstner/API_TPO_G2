package com.tpo_G2.ecommerce.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CarritoDTO {
    private Long id;
    private UsuarioDTO usuario;
    private List<ItemCarritoDTO> items;
}
