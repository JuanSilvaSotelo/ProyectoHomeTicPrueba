package com.ventas.ventasbackend.model;

// Importaciones necesarias para la definición de la entidad Vendedor.
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

// Anotación que marca esta clase como una entidad JPA.
@Entity
// Anotación para ignorar propiedades específicas durante la serialización/deserialización JSON,
// útil para evitar errores con proxies de Hibernate.
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Vendedor {
    // Define la clave primaria de la entidad.
    @Id
    // Configura la estrategia de generación de valores para la clave primaria (identidad).
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Mapea la propiedad a la columna 'VendedorID' en la base de datos.
    @Column(name = "VendedorID")
    private Integer idVendedor;
    // Propiedad para el nombre del vendedor.
    private String nombre;
    // Propiedad para el apellido del vendedor.
    private String apellido;
    // Propiedad para el correo electrónico del vendedor.
    private String email;
    // Propiedad para el número de teléfono del vendedor.
    private String telefono;

    // Constructor por defecto.
    public Vendedor() {
    }

    // Getters y Setters para acceder y modificar las propiedades de la entidad Vendedor.

    // Obtiene el ID del vendedor.
    public Integer getIdVendedor() {
        return idVendedor;
    }

    // Establece el ID del vendedor.
    public void setIdVendedor(Integer idVendedor) {
        this.idVendedor = idVendedor;
    }

    // Obtiene el nombre del vendedor.
    public String getNombre() {
        return nombre;
    }

    // Establece el nombre del vendedor.
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Obtiene el apellido del vendedor.
    public String getApellido() {
        return apellido;
    }

    // Establece el apellido del vendedor.
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    // Obtiene el correo electrónico del vendedor.
    public String getEmail() {
        return email;
    }

    // Establece el correo electrónico del vendedor.
    public void setEmail(String email) {
        this.email = email;
    }

    // Obtiene el número de teléfono del vendedor.
    public String getTelefono() {
        return telefono;
    }

    // Establece el número de teléfono del vendedor.
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}