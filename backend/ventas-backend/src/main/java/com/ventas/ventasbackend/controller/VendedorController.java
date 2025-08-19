package com.ventas.ventasbackend.controller;

// Importaciones necesarias para el controlador de vendedores.
import com.ventas.ventasbackend.model.Vendedor;
import com.ventas.ventasbackend.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// Anotación que indica que esta clase es un controlador REST.
@RestController
// Anotación para mapear las solicitudes a la ruta /api/vendedores.
@RequestMapping("/api/vendedores")
public class VendedorController {

    // Inyección de dependencia del repositorio de vendedores.
    @Autowired
    private VendedorRepository vendedorRepository;

    // Método para obtener todos los vendedores.
    // Mapea las solicitudes GET a la ruta /api/vendedores.
    @GetMapping
    public List<Vendedor> getAllVendedores() {
        return vendedorRepository.findAll();
    }

    // Método para obtener un vendedor por su ID.
    // Mapea las solicitudes GET a la ruta /api/vendedores/{id}.
    @GetMapping("/{id}")
    public ResponseEntity<Vendedor> getVendedorById(@PathVariable Long id) {
        Optional<Vendedor> vendedor = vendedorRepository.findById(id);
        // Retorna el vendedor si se encuentra, de lo contrario, retorna un 404 Not Found.
        return vendedor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Método para crear un nuevo vendedor.
    // Mapea las solicitudes POST a la ruta /api/vendedores.
    @PostMapping
    public Vendedor createVendedor(@RequestBody Vendedor vendedor) {
        return vendedorRepository.save(vendedor);
    }

    // Método para actualizar un vendedor existente por su ID.
    // Mapea las solicitudes PUT a la ruta /api/vendedores/{id}.
    @PutMapping("/{id}")
    public ResponseEntity<Vendedor> updateVendedor(@PathVariable Long id, @RequestBody Vendedor vendedorDetails) {
        Optional<Vendedor> vendedor = vendedorRepository.findById(id);
        // Si el vendedor existe, actualiza sus detalles.
        if (vendedor.isPresent()) {
            Vendedor existingVendedor = vendedor.get();
            existingVendedor.setNombre(vendedorDetails.getNombre());
            existingVendedor.setEmail(vendedorDetails.getEmail());
            existingVendedor.setTelefono(vendedorDetails.getTelefono());
            return ResponseEntity.ok(vendedorRepository.save(existingVendedor));
        } else {
            // Si el vendedor no se encuentra, retorna un 404 Not Found.
            return ResponseEntity.notFound().build();
        }
    }

    // Método para eliminar un vendedor por su ID.
    // Mapea las solicitudes DELETE a la ruta /api/vendedores/{id}.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendedor(@PathVariable Long id) {
        // Si el vendedor existe, lo elimina.
        if (vendedorRepository.existsById(id)) {
            vendedorRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            // Si el vendedor no se encuentra, retorna un 404 Not Found.
            return ResponseEntity.notFound().build();
        }
    }
}