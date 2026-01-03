package com.crisvera.supermercado.service;

import com.crisvera.supermercado.dto.DetalleVentaDTO;
import com.crisvera.supermercado.dto.VentaDTO;
import com.crisvera.supermercado.exception.NotFoundException;
import com.crisvera.supermercado.mapper.Mapper;
import com.crisvera.supermercado.model.DetalleVenta;
import com.crisvera.supermercado.model.Producto;
import com.crisvera.supermercado.model.Sucursal;
import com.crisvera.supermercado.model.Venta;
import com.crisvera.supermercado.repository.ProductoRepository;
import com.crisvera.supermercado.repository.SucursalRepository;
import com.crisvera.supermercado.repository.VentaRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VentaService implements IVentaService {

    @Autowired
    private VentaRepository ventaRepo;
    @Autowired
    private ProductoRepository prodRepo;
    @Autowired
    private SucursalRepository sucRepo;


    @Override
    public List<VentaDTO> traerVentas() {
        List<Venta> ventas = ventaRepo.findAll();
        List<VentaDTO> ventasDto = new ArrayList<>();

        VentaDTO dto;

        for (Venta v : ventas) {
            dto = Mapper.toDto(v);
            ventasDto.add(dto);
        }

        return ventasDto;
    }

    @Override
    public VentaDTO crearVenta(VentaDTO dto) {
        // Validaciones
        if(dto == null) throw new RuntimeException("Venta es null");
        if(dto.getSucursalId() == null) throw new RuntimeException("Se debe indicar una sucursal");
        if(dto.getDetalle() == null || dto.getDetalle().isEmpty())
            throw new RuntimeException("Debe tener almenos un producto en el detalle");

        // Buscar la surcursal
        Sucursal suc = sucRepo.findById(dto.getSucursalId())
                .orElseThrow(() -> new NotFoundException("La Sucursal no encontrada"));

        // Crear la venta
        Venta vent = new Venta();
        vent.setFecha(dto.getFecha());
        vent.setEstado(dto.getEstado());
        vent.setSucursal(suc);
        vent.setTotal(dto.getTotal());

        // Lista de detalles (productos)
        List<DetalleVenta> detalles = new ArrayList<>();

        for(DetalleVentaDTO d : dto.getDetalle()) {
            Producto p = prodRepo.findByNombre(d.getProdNombre())
                    .orElseThrow(() -> new NotFoundException("Producto no encontrado: " + d.getId()));

            // Crear detalle
            DetalleVenta detalleVenta = new DetalleVenta();
            detalleVenta.setProducto(p);
            detalleVenta.setVenta(vent);
            detalleVenta.setPrecio(p.getPrecio());
            detalleVenta.setCantidad(d.getCantidad());

            detalles.add(detalleVenta);
        }

        // Setear la lista de detalles venta
        vent.setDetalle(detalles);

        // Guardar en la bd
        Venta savedVenta = ventaRepo.save(vent);

        return Mapper.toDto(savedVenta);

    }

    @Override
    public VentaDTO actualizarVenta(Long id, VentaDTO dto) {
        // Buscar la venta
        Venta v = ventaRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Venta no encontrada"));

        if(dto.getFecha() != null) {
            v.setFecha(dto.getFecha());
        }
        if(dto.getEstado() != null) {
            v.setEstado(dto.getEstado());
        }
        if(dto.getTotal() != null) {
            v.setTotal(dto.getTotal());
        }
        if(dto.getSucursalId() != null) {
            Sucursal suc = sucRepo.findById(id)
                    .orElseThrow(() -> new NotFoundException("Sucursal no encontrada"));
            v.setSucursal(suc);
        }

        Venta savedVenta = ventaRepo.save(v);

        return Mapper.toDto(savedVenta);
    }

    @Override
    public void eliminarVenta(Long id) {
        if(!ventaRepo.existsById(id)) {
            throw new NotFoundException("Venta no encontrada para eliminar");
        }
        ventaRepo.deleteById(id);
    }
}
