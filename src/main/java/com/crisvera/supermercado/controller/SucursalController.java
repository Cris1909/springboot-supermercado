package com.crisvera.supermercado.controller;

import com.crisvera.supermercado.dto.SucursalDTO;
import com.crisvera.supermercado.service.SucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/api/sucursales")
public class SucursalController {
    @Autowired
    private SucursalService sucursalService;

    @GetMapping
    public ResponseEntity<List<SucursalDTO>> traerSucursales() {
        List<SucursalDTO> res = sucursalService.traerSucursales();
        return ResponseEntity.ok(res);
    }

    @PostMapping
    public ResponseEntity<SucursalDTO> crearSucursal(@RequestBody SucursalDTO dto) {
        SucursalDTO created = sucursalService.crearSucursal(dto);
        return ResponseEntity
                .created(URI.create("/api/sucursales " + created.getId() ))
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SucursalDTO> actualizarSucursal(
            @PathVariable Long id,
            @RequestBody SucursalDTO dto
    ) {
        SucursalDTO created = sucursalService.actualizarSucursal(id, dto);
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSucursal(@PathVariable Long id) {
        sucursalService.eliminarSucursal(id);
        return ResponseEntity.noContent().build();
    }
}
