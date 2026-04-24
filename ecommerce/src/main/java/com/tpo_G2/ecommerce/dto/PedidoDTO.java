package com.tpo_G2.ecommerce.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PedidoDTO {
    private Long id;
    private Date fecha;
    private String estado;
    private double total;
    private UsuarioDTO usuario;
    private List<ItemPedidoDTO> items;
}
