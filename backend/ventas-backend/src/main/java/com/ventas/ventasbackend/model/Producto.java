package com.ventas.ventasbackend.model;

// Importaciones necesarias para la definición de la entidad Producto.
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import java.math.BigDecimal;

// Anotación que marca esta clase como una entidad JPA.
@Entity
// Anotación para ignorar propiedades específicas durante la serialización/deserialización JSON,
// útil para evitar errores con proxies de Hibernate.
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Producto {
    // Define la clave primaria de la entidad.
    @Id
    // Configura la estrategia de generación de valores para la clave primaria (identidad).
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Mapea la propiedad a la columna 'ProductoID' en la base de datos.
    @Column(name = "ProductoID")
    private Integer idProducto;
    // Propiedad para el nombre del producto.
    private String nombre;
    // Propiedad para la descripción del producto.
    private String descripcion;
    // Propiedad para el precio del producto, usando BigDecimal para precisión monetaria.
    private BigDecimal precio;
    // Propiedad para la cantidad en stock del producto.
    private Integer stock;

    // Getters y Setters para acceder y modificar las propiedades de la entidad Producto.

    // Obtiene el ID del producto.
    public Integer getIdProducto() {
        return idProducto;
    }

    // Establece el ID del producto.
    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    // Obtiene el nombre del producto.
    public String getNombre() {
        return nombre;
    }

    // Establece el nombre del producto.
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Obtiene la descripción del producto.
    public String getDescripcion() {
        return descripcion;
    }

    // Establece la descripción del producto.
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // Obtiene el precio del producto.
    public BigDecimal getPrecio() {
        return precio;
    }

    // Establece el precio del producto.
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    // Obtiene el stock del producto.
    public Integer getStock() {
        return stock;
    }

    // Establece el stock del producto.
    public void setStock(Integer stock) {
        this.stock = stock;
    }
}