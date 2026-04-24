package com.tpo_G2.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemPedidoDTO {
    private Long id;
    private int cantidad;
    private double precioUnitario;
    private ProductoDTO producto;
}
