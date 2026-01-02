package com.crisvera.supermercado.mapper;

import com.crisvera.supermercado.dto.DetalleVentaDTO;
import com.crisvera.supermercado.dto.ProductoDTO;
import com.crisvera.supermercado.dto.SucursalDTO;
import com.crisvera.supermercado.dto.VentaDTO;
import com.crisvera.supermercado.model.DetalleVenta;
import com.crisvera.supermercado.model.Producto;
import com.crisvera.supermercado.model.Sucursal;
import com.crisvera.supermercado.model.Venta;

import java.util.List;
import java.util.stream.Collectors;

public class Mapper {

    // Producto a ProductoDTO
    public static ProductoDTO toDto(Producto p) {
        if (p == null) return null;

        return ProductoDTO.builder()
                .id(p.getId())
                .nombre(p.getNombre())
                .categoria(p.getCategoria())
                .precio(p.getPrecio())
                .build();

    }

    // DetalleVenta a DetalleVentaDTO
    public static DetalleVentaDTO toDto(DetalleVenta d) {
        if (d == null) return null;

        Double subtotal = d.getPrecio() * d.getCantidad();

        return DetalleVentaDTO.builder()
                .id(d.getId())
                .prodNombre(d.getProducto().getNombre())
                .cantidad(d.getCantidad())
                .precio(d.getPrecio())
                .subtotal(subtotal)
                .build();

    }

    // Venta a VentaDTO
    public static VentaDTO toDto(Venta v) {
        if (v == null) {
            return null;
        }

        List<DetalleVentaDTO> detalle = v.getDetalle().stream().map(Mapper::toDto).collect(Collectors.toList());

        Double total = detalle.stream()
                .map(DetalleVentaDTO::getSubtotal)
                .reduce(0.0, Double::sum);

        return VentaDTO.builder()
                .id(v.getId())
                .fecha(v.getFecha())
                .estado(v.getEstado())
                .sucursalId(v.getSucursal().getId())
                .total(total)
                .detalle(detalle)
                .build();
    }

    // Sucursal a SucursalDTO
    public static SucursalDTO toDto(Sucursal s) {
        if (s == null) return null;

        return SucursalDTO.builder()
                .id(s.getId())
                .nombre(s.getNombre())
                .direccion(s.getDireccion())
                .build();

    }
}
