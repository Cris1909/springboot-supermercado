package com.crisvera.supermercado.service;

import com.crisvera.supermercado.dto.ProductoDTO;
import com.crisvera.supermercado.exception.NotFoundException;
import com.crisvera.supermercado.mapper.Mapper;
import com.crisvera.supermercado.model.Producto;
import com.crisvera.supermercado.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService implements IProductoService {

    @Autowired
    private ProductoRepository repo;

    @Override
    public List<ProductoDTO> traerProductos() {

        return repo.findAll()
                .stream()
                .map(Mapper::toDto)
                .toList();
    }

    @Override
    public ProductoDTO crearProducto(ProductoDTO dto) {
        Producto prod = Producto.builder()
                .nombre(dto.getNombre())
                .categoria(dto.getCategoria())
                .precio(dto.getPrecio())
                .cantidad(dto.getCantidad())
                .build();
        return Mapper.toDto(repo.save(prod));
    }

    @Override
    public ProductoDTO actualizarProducto(Long id, ProductoDTO dto) {
        Producto prod = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));

        prod.setNombre(dto.getNombre());
        prod.setCategoria(dto.getCategoria());
        prod.setCantidad(dto.getCantidad());
        prod.setPrecio(dto.getPrecio());

        return Mapper.toDto(repo.save(prod));
    }

    @Override
    public void eliminarProducto(Long id) {
         if(!repo.existsById(id)) {
             throw new NotFoundException("Producto no encontrado para eliminar");
         }
         repo.deleteById(id);
    }
}
