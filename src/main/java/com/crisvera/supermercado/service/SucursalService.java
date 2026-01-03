package com.crisvera.supermercado.service;

import com.crisvera.supermercado.dto.SucursalDTO;
import com.crisvera.supermercado.exception.NotFoundException;
import com.crisvera.supermercado.mapper.Mapper;
import com.crisvera.supermercado.model.Sucursal;
import com.crisvera.supermercado.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SucursalService implements ISucursalService {

    @Autowired
    private SucursalRepository repo;

    @Override
    public List<SucursalDTO> traerSucursales() {
        return repo.findAll()
                .stream()
                .map(Mapper::toDto)
                .toList();
    }

    @Override
    public SucursalDTO crearSucursal(SucursalDTO dto) {
        Sucursal suc = Sucursal.builder()
                .nombre(dto.getNombre())
                .direccion(dto.getDireccion())
                .build();

        return Mapper.toDto(repo.save(suc));
    }

    @Override
    public SucursalDTO actualizarSucursal(Long id, SucursalDTO dto) {
        Sucursal suc = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Sucursal no encontrada"));

        suc.setDireccion(dto.getDireccion());
        suc.setNombre(dto.getNombre());

        return  Mapper.toDto(repo.save(suc));
    }

    @Override
    public void eliminarSucursal(Long id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("Sucursal no encontrada para eliminar");
        }

        repo.deleteById(id);
    }
}
