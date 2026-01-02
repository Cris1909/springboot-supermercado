package com.crisvera.supermercado.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaDTO {
    private Long id;
    private LocalDate fecha;
    private String estado;

    // datos sucursal
    private Long sucursalId;

    // lista de detalles
    private List<DetalleVentaDTO> detalle;

    // total de la venta
    private Double total;
}
