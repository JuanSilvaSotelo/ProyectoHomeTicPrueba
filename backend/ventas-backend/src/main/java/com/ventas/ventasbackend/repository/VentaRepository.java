package com.ventas.ventasbackend.repository;

// Importaciones necesarias para la interfaz del repositorio de ventas.
import com.ventas.ventasbackend.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Anotación que indica que esta interfaz es un componente de repositorio de Spring.
@Repository
// Interfaz del repositorio para la entidad Venta.
// Extiende JpaRepository para proporcionar operaciones CRUD básicas para la entidad Venta,
// con Integer como el tipo de la clave primaria.
public interface VentaRepository extends JpaRepository<Venta, Integer> {
    // Esta interfaz no necesita métodos adicionales ya que JpaRepository proporciona
    // todas las operaciones CRUD comunes.
}