package com.ventas.ventasbackend.controller;

// Importaciones necesarias para el controlador de ventas.
import com.ventas.ventasbackend.model.Venta;
import com.ventas.ventasbackend.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// Anotación que indica que esta clase es un controlador REST.
@RestController
// Anotación para mapear las solicitudes a la ruta /api/ventas.
@RequestMapping("/api/ventas")
// Anotación para permitir solicitudes de origen cruzado desde http://localhost:3000.
@CrossOrigin(origins = "http://localhost:3000")
public class VentaController {

    // Inyección de dependencia del repositorio de ventas.
    @Autowired
    private VentaRepository ventaRepository;

    // Método para obtener todas las ventas.
    // Mapea las solicitudes GET a la ruta /api/ventas.
    @GetMapping
    public List<Venta> getAllVentas() {
        List<Venta> ventas = ventaRepository.findAll();
        System.out.println("Fetched ventas: " + ventas);
        return ventas;
    }

    // Método para obtener una venta por su ID.
    // Mapea las solicitudes GET a la ruta /api/ventas/{id}.
    @GetMapping("/{id}")
    public ResponseEntity<Venta> getVentaById(@PathVariable Integer id) {
        Optional<Venta> venta = ventaRepository.findById(id);
        // Retorna la venta si se encuentra, de lo contrario, retorna un 404 Not Found.
        return venta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Método para crear una nueva venta.
    // Mapea las solicitudes POST a la ruta /api/ventas.
    @PostMapping
    public Venta createVenta(@RequestBody Venta venta) {
        return ventaRepository.save(venta);
    }

    // Método para actualizar una venta existente por su ID.
    // Mapea las solicitudes PUT a la ruta /api/ventas/{id}.
    @PutMapping("/{id}")
    public ResponseEntity<Venta> updateVenta(@PathVariable Integer id, @RequestBody Venta ventaDetails) {
        Optional<Venta> venta = ventaRepository.findById(id);
        // Si la venta existe, actualiza sus detalles.
        if (venta.isPresent()) {
            Venta existingVenta = venta.get();
            existingVenta.setFechaVenta(ventaDetails.getFechaVenta());
            existingVenta.setTotal(ventaDetails.getTotal());
            existingVenta.setProducto(ventaDetails.getProducto());
            existingVenta.setVendedor(ventaDetails.getVendedor());
            return ResponseEntity.ok(ventaRepository.save(existingVenta));
        } else {
            // Si la venta no se encuentra, retorna un 404 Not Found.
            return ResponseEntity.notFound().build();
        }
    }

    // Método para eliminar una venta por su ID.
    // Mapea las solicitudes DELETE a la ruta /api/ventas/{id}.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenta(@PathVariable Integer id) {
        // Si la venta existe, la elimina.
        if (ventaRepository.existsById(id)) {
            ventaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            // Si la venta no se encuentra, retorna un 404 Not Found.
            return ResponseEntity.notFound().build();
        }
    }
}