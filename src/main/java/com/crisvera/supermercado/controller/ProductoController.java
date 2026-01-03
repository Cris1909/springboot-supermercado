package com.crisvera.supermercado.controller;

import com.crisvera.supermercado.dto.ProductoDTO;
import com.crisvera.supermercado.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> traerProductos() {
        List<ProductoDTO> res = productoService.traerProductos();
        return ResponseEntity.ok(res);
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> crearProducto(ProductoDTO dto) {
        ProductoDTO created = productoService.crearProducto(dto);
        return ResponseEntity
                .created(URI.create("/api/productos " + created.getId() ))
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizarProducto(
            @PathVariable Long id,
            ProductoDTO dto
    ) {
        ProductoDTO created = productoService.actualizarProducto(id, dto);
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
         productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
