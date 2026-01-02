package com.crisvera.supermercado.service;

import com.crisvera.supermercado.dto.VentaDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentaService implements IVentaService {
    @Override
    public List<VentaDTO> traerVentas() {
        return null;
    }

    @Override
    public VentaDTO crearVenta(VentaDTO ventaDto) {
        return null;
    }

    @Override
    public VentaDTO actualizarVenta(Long id, VentaDTO ventaDTO) {
        return null;
    }

    @Override
    public void eliminarVenta(Long id) {

    }
}
