package com.ventas.ventasbackend.model;

// Importaciones necesarias para la definición de la entidad User.
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// Anotación que marca esta clase como una entidad JPA.
@Entity
public class User {
    // Define la clave primaria de la entidad.
    @Id
    // Configura la estrategia de generación de valores para la clave primaria (identidad).
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Propiedad para el nombre de usuario.
    private String username;
    // Propiedad para la contraseña del usuario.
    private String password;
    private int failedLoginAttempts;
    private Long lockTime; // Timestamp when the account was locked
    private boolean accountLocked;

    // Getters y Setters para acceder y modificar las propiedades de la entidad User.

    // Obtiene el ID del usuario.
    public Long getId() {
        return id;
    }

    // Establece el ID del usuario.
    public void setId(Long id) {
        this.id = id;
    }

    // Obtiene el nombre de usuario.
    public String getUsername() {
        return username;
    }

    // Establece el nombre de usuario.
    public void setUsername(String username) {
        this.username = username;
    }

    // Obtiene la contraseña del usuario.
    public String getPassword() {
        return password;
    }

    // Establece la contraseña del usuario.
    public void setPassword(String password) {
        this.password = password;
    }

    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public Long getLockTime() {
        return lockTime;
    }

    public void setLockTime(Long lockTime) {
        this.lockTime = lockTime;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }
}