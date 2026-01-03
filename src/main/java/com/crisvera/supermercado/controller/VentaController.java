package com.crisvera.supermercado.controller;

import com.crisvera.supermercado.dto.VentaDTO;
import com.crisvera.supermercado.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<VentaDTO>> traerVentas() {
        List<VentaDTO> res = ventaService.traerVentas();
        return ResponseEntity.ok(res);
    }

    @PostMapping
    public ResponseEntity<VentaDTO> crearVenta(@RequestBody VentaDTO dto) {
        VentaDTO created = ventaService.crearVenta(dto);
        return ResponseEntity
                .created(URI.create("/api/ventas " + created.getId() ))
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentaDTO> actualizarVenta(
            @PathVariable Long id,
            @RequestBody VentaDTO dto
    ) {
        VentaDTO created = ventaService.actualizarVenta(id, dto);
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id) {
        ventaService.eliminarVenta(id);
        return ResponseEntity.noContent().build();
    }
    
}
