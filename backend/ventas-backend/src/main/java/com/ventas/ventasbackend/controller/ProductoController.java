package com.ventas.ventasbackend.controller;

// Importaciones necesarias para el controlador de productos.
import com.ventas.ventasbackend.model.Producto;
import com.ventas.ventasbackend.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// Anotación que indica que esta clase es un controlador REST.
@RestController
// Anotación para mapear las solicitudes a la ruta /api/productos.
@RequestMapping("/api/productos")
public class ProductoController {

    // Inyección de dependencia del repositorio de productos.
    @Autowired
    private ProductoRepository productoRepository;

    // Método para obtener todos los productos.
    // Mapea las solicitudes GET a la ruta /api/productos.
    @GetMapping
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    // Método para obtener un producto por su ID.
    // Mapea las solicitudes GET a la ruta /api/productos/{id}.
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        Optional<Producto> producto = productoRepository.findById(id);
        // Retorna el producto si se encuentra, de lo contrario, retorna un 404 Not Found.
        return producto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Método para crear un nuevo producto.
    // Mapea las solicitudes POST a la ruta /api/productos.
    @PostMapping
    public Producto createProducto(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

    // Método para actualizar un producto existente por su ID.
    // Mapea las solicitudes PUT a la ruta /api/productos/{id}.
    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable Long id, @RequestBody Producto productoDetails) {
        Optional<Producto> producto = productoRepository.findById(id);
        // Si el producto existe, actualiza sus detalles.
        if (producto.isPresent()) {
            Producto existingProducto = producto.get();
            existingProducto.setNombre(productoDetails.getNombre());
            existingProducto.setDescripcion(productoDetails.getDescripcion());
            existingProducto.setPrecio(productoDetails.getPrecio());
            existingProducto.setStock(productoDetails.getStock());
            return ResponseEntity.ok(productoRepository.save(existingProducto));
        } else {
            // Si el producto no se encuentra, retorna un 404 Not Found.
            return ResponseEntity.notFound().build();
        }
    }

    // Método para eliminar un producto por su ID.
    // Mapea las solicitudes DELETE a la ruta /api/productos/{id}.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        // Si el producto existe, lo elimina.
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            // Si el producto no se encuentra, retorna un 404 Not Found.
            return ResponseEntity.notFound().build();
        }
    }
}