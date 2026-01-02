package com.crisvera.supermercado.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleVentaDTO {
    private Long id;
    private String prodNombre;
    private Integer cantidad;
    private Double precio;
    private Double subtotal;
}
