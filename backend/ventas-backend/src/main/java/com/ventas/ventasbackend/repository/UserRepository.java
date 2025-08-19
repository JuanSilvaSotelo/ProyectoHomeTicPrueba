package com.ventas.ventasbackend.repository;

// Importaciones necesarias para la interfaz del repositorio de usuarios.
import com.ventas.ventasbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// Interfaz del repositorio para la entidad User.
// Extiende JpaRepository para proporcionar operaciones CRUD básicas para la entidad User,
// con Long como el tipo de la clave primaria.
public interface UserRepository extends JpaRepository<User, Long> {
    // Método para encontrar un usuario por su nombre de usuario.
    // Spring Data JPA generará automáticamente la consulta SQL para este método.
    User findByUsername(String username);
}