package com.ventas.ventasbackend.repository;

// Importaciones necesarias para la interfaz del repositorio de productos.
import com.ventas.ventasbackend.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Anotación que indica que esta interfaz es un componente de repositorio de Spring.
@Repository
// Interfaz del repositorio para la entidad Producto.
// Extiende JpaRepository para proporcionar operaciones CRUD básicas para la entidad Producto,
// con Long como el tipo de la clave primaria.
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Esta interfaz no necesita métodos adicionales ya que JpaRepository proporciona
    // todas las operaciones CRUD comunes.
}