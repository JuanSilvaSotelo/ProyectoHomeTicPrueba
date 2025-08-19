package com.ventas.ventasbackend.repository;

// Importaciones necesarias para la interfaz del repositorio de vendedores.
import com.ventas.ventasbackend.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Anotación que indica que esta interfaz es un componente de repositorio de Spring.
@Repository
// Interfaz del repositorio para la entidad Vendedor.
// Extiende JpaRepository para proporcionar operaciones CRUD básicas para la entidad Vendedor,
// con Long como el tipo de la clave primaria.
public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
    // Esta interfaz no necesita métodos adicionales ya que JpaRepository proporciona
    // todas las operaciones CRUD comunes.
}